package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.dictionary.Currency;
import dgcd.financier.app.dictionary.OperationType;
import dgcd.financier.app.modules.dataExcel.exceptions.ImportingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.ACCOUNT_COLUMNS_COUNT;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.CATEGORIY_COLUMNS_COUNT;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.OPERATION_COLUMNS_COUNT;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.SHEET_ACCOUNTS;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.SHEET_CATEGORIES;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.SHEET_OPERATIONS;
import static java.util.Objects.isNull;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
class DataExcelParseService {

    @SneakyThrows
    ParsedData parseDataFromExcel(InputStream inputStream) {
        try (var workbook = new XSSFWorkbook(inputStream)) {
            return new ParsedData(
                    parseAccounts(workbook),
                    parseCategories(workbook),
                    parseOperations(workbook)
            );
        }
    }


    private List<ParsedData.ParsedAccount> parseAccounts(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_ACCOUNTS);
        var accounts = new ArrayList<ParsedData.ParsedAccount>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, ACCOUNT_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedAccount = new ParsedData.ParsedAccount(
                    readString(row, cellNum++),
                    readEnum(row, cellNum++, Currency.class),
                    readBigDecimal(row, cellNum++),
                    readBoolean(row, cellNum)
            );
            accounts.add(parsedAccount);
        }
        return accounts;
    }


    private List<ParsedData.ParsedCategory> parseCategories(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_CATEGORIES);
        var categories = new ArrayList<ParsedData.ParsedCategory>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, CATEGORIY_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedCategory = new ParsedData.ParsedCategory(
                    readString(row, cellNum++),
                    readString(row, cellNum)
            );
            categories.add(parsedCategory);
        }
        return categories;
    }


    private List<ParsedData.ParsedOperation> parseOperations(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_OPERATIONS);
        var operations = new ArrayList<ParsedData.ParsedOperation>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, OPERATION_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedOperation = new ParsedData.ParsedOperation(
                    readLocalDate(row, cellNum++),
                    readString(row, cellNum++),
                    readBigDecimal(row, cellNum++),
                    readBigDecimal(row, cellNum++),
                    readEnum(row, cellNum++, OperationType.class),
                    readString(row, cellNum++),
                    readString(row, cellNum++),
                    readString(row, cellNum++),
                    readString(row, cellNum++),
                    readString(row, cellNum)
            );
            operations.add(parsedOperation);
        }
        return operations;
    }

    /////////////////////////////////////////////////////////

    private XSSFSheet validateAndGetSheet(XSSFWorkbook workbook, String title) {
        var sheet = workbook.getSheet(title);
        if (isNull(sheet)) {
            var msg = String.format("Sheet with name '%s' is not found", title);
            throw new ImportingException(msg);
        }
        return sheet;
    }


    private boolean isEmptyRow(XSSFRow row, int columnsCount) {
        if (isNull(row)) {
            return true;
        }
        for (var i = 0; i < columnsCount; i++) {
            var cell = row.getCell(i);
            if (!(isNull(cell) || BLANK.equals(cell.getCellType()))) {
                return false;
            }
        }
        return true;
    }


    private String readString(XSSFRow row, int cellNum) {
        var cell = row.getCell(cellNum);
        if (isNull(cell) || BLANK.equals(cell.getCellType())) {
            return null;
        }
        try {
            var stringValue = cell.getStringCellValue();
            return hasText(stringValue) ?
                    stringValue.trim() :
                    null;
        } catch (RuntimeException exception) {
            log.warn("Parsing error: " + exception.getMessage());
            throw new ImportingException("Parsing error: " + exception.getMessage());
        }
    }


    private LocalDate readLocalDate(XSSFRow row, int cellNum) {
        var cell = row.getCell(cellNum);
        if (isNull(cell) || BLANK.equals(cell.getCellType())) {
            return null;
        }
        try {
            var stringValue = cell.getStringCellValue();
            return hasText(stringValue) ?
                    LocalDate.parse(stringValue.trim()) :
                    null;
        } catch (RuntimeException exception) {
            log.warn("Parsing error: " + exception.getMessage());
            throw new ImportingException("Parsing error: " + exception.getMessage());
        }
    }


    private <T extends Enum<T>> T readEnum(XSSFRow row, int cellNum, Class<T> enumClass) {
        var cell = row.getCell(cellNum);
        if (isNull(cell) || BLANK.equals(cell.getCellType())) {
            return null;
        }

        var stringValue = cell.getStringCellValue();
        if (!hasText(stringValue)) {
            return null;
        }
        stringValue = stringValue.trim();

        try {
            return Enum.valueOf(enumClass, stringValue);
        } catch (RuntimeException exception) {
            log.warn("Parsing error: " + exception.getMessage());
            throw new ImportingException("Parsing error: " + exception.getMessage());
        }
    }


    private BigDecimal readBigDecimal(XSSFRow row, int cellNum) {
        var cell = row.getCell(cellNum);
        if (isNull(cell) || BLANK.equals(cell.getCellType())) {
            return null;
        }
        try {
            return BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros();
        } catch (RuntimeException exception) {
            log.warn("Parsing error: " + exception.getMessage());
            throw new ImportingException("Parsing error: " + exception.getMessage());
        }
    }


    private Boolean readBoolean(XSSFRow row, int cellNum) {
        var cell = row.getCell(cellNum);
        if (isNull(cell) || BLANK.equals(cell.getCellType())) {
            return null;
        }
        try {
            return cell.getBooleanCellValue();
        } catch (RuntimeException exception) {
            log.warn("Parsing error: " + exception.getMessage());
            throw new ImportingException("Parsing error: " + exception.getMessage());
        }
    }

}
