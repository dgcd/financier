package dgcd.financier.port.gateway.service.alldata;

import dgcd.financier.core.usecase.api.dto.AlldataDto;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_ACCOUNTS;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_CATEGORIES;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_OPERATIONS;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_RATES;

@Service
@RequiredArgsConstructor
public class AlldataGenerateExcelService {

    public void generateExcel(
            ServletOutputStream outputStream,
            AlldataDto alldataRows
    ) throws IOException {
        try (var workbook = new XSSFWorkbook()) {
            writeAccounts(workbook, alldataRows.accounts());
            writeCategories(workbook, alldataRows.categories());
            writeOperations(workbook, alldataRows.operations());
            writeRates(workbook, alldataRows.rates());
            workbook.write(outputStream);
        }
    }

    /////////////////////////////////////////////////////////////////////////////


    private void writeAccounts(XSSFWorkbook workbook, List<AlldataDto.AccountRow> accounts) {
        var sheet = workbook.createSheet(SHEET_ACCOUNTS);
        var rowInx = 0;
        for (var account : accounts) {
            writeAccountRow(sheet, rowInx++, account);
        }
    }

    private void writeAccountRow(XSSFSheet sheet, int rowInx, AlldataDto.AccountRow account) {
        var row = sheet.createRow(rowInx);
        var colInx = 0;
        createCell(row, colInx++, account.title());
        createCell(row, colInx++, account.currency());
        createCell(row, colInx++, account.balance());
        createCell(row, colInx, account.isClosed());
    }

    /////////////////////////////////////////////////////////////////////////////


    private void writeCategories(XSSFWorkbook workbook, List<AlldataDto.CategoryRow> categories) {
        var sheet = workbook.createSheet(SHEET_CATEGORIES);
        var rowInx = 0;
        for (var category : categories) {
            writeCategoryRow(sheet, rowInx++, category);
        }
    }

    private void writeCategoryRow(XSSFSheet sheet, int rowInx, AlldataDto.CategoryRow category) {
        var row = sheet.createRow(rowInx);
        var colInx = 0;
        createCell(row, colInx++, category.parentTitle());
        createCell(row, colInx, category.ownTitle());
    }

    /////////////////////////////////////////////////////////////////////////////


    private void writeOperations(XSSFWorkbook workbook, List<AlldataDto.OperationRow> operations) {
        var sheet = workbook.createSheet(SHEET_OPERATIONS);
        var rowInx = 0;
        for (var operation : operations) {
            writeOperationRow(sheet, rowInx++, operation);
        }
    }

    private void writeOperationRow(XSSFSheet sheet, int rowInx, AlldataDto.OperationRow operation) {
        var row = sheet.createRow(rowInx);
        var colInx = 0;
        createCell(row, colInx++, operation.date());
        createCell(row, colInx++, operation.accountTitle());
        createCell(row, colInx++, operation.amount());
        createCell(row, colInx++, operation.quantity());
        createCell(row, colInx++, operation.type());
        createCell(row, colInx++, operation.parentCategoryTitle());
        createCell(row, colInx++, operation.subcategoryTitle());
        createCell(row, colInx++, operation.comment());
        createCell(row, colInx++, operation.counterparty());
        createCell(row, colInx, operation.correlationId());
    }

    /////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////


    private void writeRates(XSSFWorkbook workbook, List<AlldataDto.RateRow> rates) {
        var sheet = workbook.createSheet(SHEET_RATES);
        var rowInx = 0;
        for (var rate : rates) {
            writeRateRow(sheet, rowInx++, rate);
        }
    }

    private void writeRateRow(XSSFSheet sheet, int rowInx, AlldataDto.RateRow rate) {
        var row = sheet.createRow(rowInx);
        var colInx = 0;
        createCell(row, colInx++, rate.date());
        createCell(row, colInx++, rate.eurRate());
        createCell(row, colInx, rate.usdRate());
    }

    /////////////////////////////////////////////////////////////////////////////


    private void createCell(XSSFRow row, int colInx, Object value) {
        var cell = row.createCell(colInx);
        switch (value) {
            case null -> cell.setBlank();
            case Enum<?> enumValue -> cell.setCellValue(enumValue.name());
            case Boolean boolValue -> cell.setCellValue(boolValue);
            case LocalDate dateValue -> cell.setCellValue(dateValue.toString());
            case Number numberValue -> cell.setCellValue(numberValue.doubleValue());
            default -> cell.setCellValue(value.toString());
        }
    }

}
