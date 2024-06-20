package dgcd.financier.port.gateway.service.alldata;

import dgcd.financier.core.domain.CurrencyType;
import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.usecase.api.dto.AlldataDto;
import dgcd.financier.core.usecase.api.dto.AlldataDto.AccountRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.CategoryRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.OperationRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.RateRow;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.ACCOUNT_COLUMNS_COUNT;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.CATEGORY_COLUMNS_COUNT;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.OPERATION_COLUMNS_COUNT;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.RATES_COLUMNS_COUNT;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_ACCOUNTS;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_CATEGORIES;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_OPERATIONS;
import static dgcd.financier.port.gateway.service.alldata.AlldataConstants.SHEET_RATES;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Objects.isNull;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("DataFlowIssue")
public class AlldataParseExcelService {

    public Either<CommonError, AlldataDto> parseDataFromExcel(InputStream inputStream) {
        try (var workbook = new XSSFWorkbook(inputStream)) {
            var alldataDto = new AlldataDto(
                    parseAccounts(workbook),
                    parseCategories(workbook),
                    parseOperations(workbook),
                    parseRates(workbook)
            );
            return right(alldataDto);
        } catch (IOException e) {
            return left(AlldataError.of(e));
        }
    }


    private List<AccountRow> parseAccounts(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_ACCOUNTS);
        var accounts = new ArrayList<AccountRow>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, ACCOUNT_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedAccount = new AccountRow(
                    readString(row, cellNum++),
                    readEnum(row, cellNum++, CurrencyType.class),
                    readBigDecimal(row, cellNum++),
                    readBoolean(row, cellNum)
            );
            accounts.add(parsedAccount);
        }
        return accounts;
    }


    private List<CategoryRow> parseCategories(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_CATEGORIES);
        var categories = new ArrayList<CategoryRow>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, CATEGORY_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedCategory = new CategoryRow(
                    readString(row, cellNum++),
                    readString(row, cellNum)
            );
            categories.add(parsedCategory);
        }
        return categories;
    }


    private List<OperationRow> parseOperations(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_OPERATIONS);
        var operations = new ArrayList<OperationRow>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, OPERATION_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedOperation = new OperationRow(
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


    private List<RateRow> parseRates(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_RATES);
        var rates = new ArrayList<RateRow>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, RATES_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedRates = new RateRow(
                    readLocalDate(row, cellNum++),
                    readBigDecimal(row, cellNum++),
                    readBigDecimal(row, cellNum++)
            );
            rates.add(parsedRates);
        }
        return rates;
    }

    /////////////////////////////////////////////////////////

    private XSSFSheet validateAndGetSheet(XSSFWorkbook workbook, String title) {
        var sheet = workbook.getSheet(title);
        if (isNull(sheet)) {
            var msg = String.format("Sheet with name '%s' is not found", title);
            throw new AlldataImportException(msg);
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
        // todo: needs refactoring, repeated exception handling
        try {
            var stringValue = cell.getStringCellValue();
            return hasText(stringValue) ?
                    stringValue.trim() :
                    null;
        } catch (RuntimeException exception) {
            logParsingError(exception);
            throw new AlldataImportException("Parsing error: " + exception.getMessage());
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
            logParsingError(exception);
            throw new AlldataImportException("Parsing error: " + exception.getMessage());
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
            logParsingError(exception);
            throw new AlldataImportException("Parsing error: " + exception.getMessage());
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
            logParsingError(exception);
            throw new AlldataImportException("Parsing error: " + exception.getMessage());
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
            logParsingError(exception);
            throw new AlldataImportException("Parsing error: " + exception.getMessage());
        }
    }


    private static void logParsingError(RuntimeException exception) {
        log.warn("Parsing error: {}", exception.getMessage());
    }

}
