package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.dictionary.Currency;
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
import java.util.ArrayList;
import java.util.List;

import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.ACCOUNT_COLUMNS_COUNT;
import static dgcd.financier.app.modules.dataExcel.DataExcelConstants.SHEET_ACCOUNTS;
import static java.util.Objects.isNull;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
class DataExcelImportService {

    @SneakyThrows
    public ImportDataResponseDto importAllDataFromExcel(ImportDataRequestDto dto) {
        try (var inputStream = dto.file().getInputStream()) {
            var parsedData = parseData(inputStream);

            parsedData.accounts().forEach(System.out::println);
            System.out.println(parsedData.accounts().size());

            return new ImportDataResponseDto(
                    List.of(),
                    List.of(),
                    List.of()
            );
        }
    }


    @SneakyThrows
    private ParsedData parseData(InputStream inputStream) {
        try (var workbook = new XSSFWorkbook(inputStream)) {
            var accounts = parseAccounts(workbook);
            return new ParsedData(accounts);
        }
    }


    private List<ParsedAccount> parseAccounts(XSSFWorkbook workbook) {
        var sheet = validateAndGetSheet(workbook, SHEET_ACCOUNTS);
        var accounts = new ArrayList<ParsedAccount>();
        var rowNum = 0;
        while (true) {
            var row = sheet.getRow(rowNum++);
            if (isEmptyRow(row, ACCOUNT_COLUMNS_COUNT)) {
                break;
            }
            var cellNum = 0;
            var parsedAccount = new ParsedAccount(
                    readString(row, cellNum++),
                    readEnum(row, cellNum++, Currency.class),
                    readBigDecimal(row, cellNum++),
                    readBoolean(row, cellNum)
            );
            accounts.add(parsedAccount);
        }
        return accounts;
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


    private XSSFSheet validateAndGetSheet(XSSFWorkbook workbook, String title) {
        var sheet = workbook.getSheet(title);
        if (isNull(sheet)) {
            var msg = String.format("Sheet with name '%s' is not found", title);
            throw new ImportingException(msg);
        }
        return sheet;
    }


    private record ParsedAccount(
            String title,
            Currency currency,
            BigDecimal balance,
            Boolean isClosed
    ) {}


    private record ParsedData(
            List<ParsedAccount> accounts
    ) {}

}
