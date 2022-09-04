package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.modules.account.Account;
import dgcd.financier.app.modules.category.Category;
import dgcd.financier.app.modules.operation.Operation;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.SHEET_ACCOUNTS;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.SHEET_CATEGORIES;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.SHEET_OPERATIONS;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
class DataExcelGenerateService {

    public void generateExcel(
            ServletOutputStream outputStream,
            AllData allDataForExport
    ) throws IOException {
        try (var workbook = new XSSFWorkbook()) {
            writeAccounts(workbook, allDataForExport.accounts());
            writeCategories(workbook, allDataForExport.categories());
            writeOperations(workbook, allDataForExport.operations());
            workbook.write(outputStream);
        }
    }

    /////////////////////////////////////////////////////////////////////////////

    private void writeAccounts(XSSFWorkbook workbook, List<Account> accounts) {
        var sheet = workbook.createSheet(SHEET_ACCOUNTS);
        var rowInx = 0;
        for (var account : accounts) {
            writeAccountRow(sheet, rowInx++, account);
        }
    }

    private void writeAccountRow(XSSFSheet sheet, int rowInx, Account account) {
        var row = sheet.createRow(rowInx);
        var colInx = 0;
        createCell(row, colInx++, account.getTitle());
        createCell(row, colInx++, account.getCurrency());
        createCell(row, colInx++, account.getBalance());
        createCell(row, colInx, account.getIsClosed());
    }

    /////////////////////////////////////////////////////////////////////////////

    private void writeCategories(XSSFWorkbook workbook, List<Category> categories) {
        var sheet = workbook.createSheet(SHEET_CATEGORIES);
        var rowInx = 0;
        for (var category : categories) {
            writeCategoryRow(sheet, rowInx++, category);
        }
    }

    private void writeCategoryRow(XSSFSheet sheet, int rowInx, Category category) {
        var row = sheet.createRow(rowInx);
        var parent = category.getParent();
        var colInx = 0;
        createCell(row, colInx++, nonNull(parent) ? parent.getTitle() : null);
        createCell(row, colInx, category.getTitle());
    }

    /////////////////////////////////////////////////////////////////////////////

    private void writeOperations(XSSFWorkbook workbook, List<Operation> operations) {
        var sheet = workbook.createSheet(SHEET_OPERATIONS);
        var rowInx = 0;
        for (var operation : operations) {
            writeOperationRow(sheet, rowInx++, operation);
        }
    }

    private void writeOperationRow(XSSFSheet sheet, int rowInx, Operation operation) {
        var row = sheet.createRow(rowInx);
        var subcategory = operation.getSubcategory();
        var parentCategory = nonNull(subcategory) ? subcategory.getParent() : null;
        var colInx = 0;
        createCell(row, colInx++, operation.getDate().toString());
        createCell(row, colInx++, operation.getAccount().getTitle());
        createCell(row, colInx++, operation.getAmount());
        createCell(row, colInx++, operation.getQuantity());
        createCell(row, colInx++, operation.getType());
        createCell(row, colInx++, nonNull(parentCategory) ? parentCategory.getTitle() : null);
        createCell(row, colInx++, nonNull(subcategory) ? subcategory.getTitle() : null);
        createCell(row, colInx++, operation.getComment());
        createCell(row, colInx++, operation.getCounterparty());
        createCell(row, colInx, operation.getCorrelationId());
    }

    /////////////////////////////////////////////////////////////////////////////

    private void createCell(XSSFRow row, int colInx, Object value) {
        var cell = row.createCell(colInx);
        switch (value) {
            case null -> cell.setBlank();
            case Enum<?> enumValue -> cell.setCellValue(enumValue.name());
            case Boolean boolValue -> cell.setCellValue(boolValue);
            case LocalDate dateValue -> cell.setCellValue(dateValue);
            case Number numberValue -> cell.setCellValue(numberValue.doubleValue());
            default -> cell.setCellValue(value.toString());
        }
    }

}
