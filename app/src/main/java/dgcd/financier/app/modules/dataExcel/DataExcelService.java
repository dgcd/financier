//package dgcd.financier.app.modules.dataExcel;
//
//import dgcd.financier.app.dictionary.Currency;
//import dgcd.financier.app.dictionary.OperationType;
//import dgcd.financier.app.modules.account.Account;
//import dgcd.financier.app.modules.category.Category;
//import dgcd.financier.app.modules.operation.Operation;
//import jakarta.servlet.ServletOutputStream;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//class DataExcelService {
//
//    private final DataExcelDatabaseService dataExcelDatabaseService;
//    private final DataExcelGenerateService dataExcelGenerateService;
//    private final DataExcelParseService dataExcelParseService;
//
//
//    public void exportAllDataToExcel(ServletOutputStream outputStream) throws IOException {
//        var allDateForExport = dataExcelDatabaseService.readDataForExport();
//        dataExcelGenerateService.generateExcel(outputStream, allDateForExport);
//    }
//
//
//    public ImportDataResponseDto importAllDataFromExcel(ImportDataRequestDto dto) {
//        ParsedData parsedData;
//        try (var inputStream = dto.file().getInputStream()) {
//            parsedData = dataExcelParseService.parseDataFromExcel(inputStream);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        dataExcelDatabaseService.saveParsedData(parsedData);
//        return new ImportDataResponseDto();
//    }
//
//}
//
//
//record AllData(
//        List<Account> accounts,
//        List<Category> categories,
//        List<Operation> operations
//) {}
//
//
//record ParsedData(
//        List<ParsedAccount> accounts,
//        List<ParsedCategory> categories,
//        List<ParsedOperation> operations
//) {
//
//    record ParsedAccount(
//            String title,
//            Currency currency,
//            BigDecimal balance,
//            Boolean isClosed
//    ) {}
//
//    record ParsedCategory(
//            String parentTitle,
//            String ownTitle
//    ) {}
//
//    record ParsedOperation(
//            LocalDate date,
//            String accountTitle,
//            BigDecimal amount,
//            BigDecimal quantity,
//            OperationType type,
//            String parentCategoryTitle,
//            String subcategoryTitle,
//            String comment,
//            String counterparty,
//            String correlationId
//    ) {}
//
//}
