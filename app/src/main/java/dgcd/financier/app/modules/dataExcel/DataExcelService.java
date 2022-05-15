package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.dictionary.Currency;
import dgcd.financier.app.dictionary.OperationType;
import dgcd.financier.app.modules.account.Account;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.Category;
import dgcd.financier.app.modules.category.dto.CategoryResponseDto;
import dgcd.financier.app.modules.operation.Operation;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
class DataExcelService {

    private final DataExcelDatabaseService dataExcelDatabaseService;
    private final DataExcelGenerateService dataExcelGenerateService;
    private final DataExcelParseService dataExcelParseService;


    public void exportAllDataToExcel(ServletOutputStream outputStream) throws IOException {
        var allDateForExport = dataExcelDatabaseService.readDataForExport();
        dataExcelGenerateService.generateExcelExcel(outputStream, allDateForExport);
    }


    @SneakyThrows
    public ImportDataResponseDto importAllDataFromExcel(ImportDataRequestDto dto) {
        ParsedData parsedData;
        try (var inputStream = dto.file().getInputStream()) {
            parsedData = dataExcelParseService.parseDataFromExcel(inputStream);
        }

        // todo: delete
        System.out.println("accounts:");
        parsedData.accounts().forEach(System.out::println);
        System.out.println(parsedData.accounts().size());

        System.out.println("categories:");
        parsedData.categories().forEach(System.out::println);
        System.out.println(parsedData.categories().size());

        System.out.println("operations:");
        parsedData.operations().forEach(System.out::println);
        System.out.println(parsedData.operations().size());

        var savedData = dataExcelDatabaseService.saveParsedData(parsedData);

        return new ImportDataResponseDto(
                savedData.accounts().stream().map(AccountResponseDto::of).toList(),
                savedData.categories().stream().map(CategoryResponseDto::of).toList(),
                savedData.operations().stream().map(OperationResponseDto::of).toList()
        );
    }

}


record AllData(
        List<Account> accounts,
        List<Category> categories,
        List<Operation> operations
) {}


record ParsedData(
        List<ParsedAccount> accounts,
        List<ParsedCategory> categories,
        List<ParsedOperation> operations
) {

    record ParsedAccount(
            String title,
            Currency currency,
            BigDecimal balance,
            Boolean isClosed
    ) {}

    record ParsedCategory(
            String parentTitle,
            String ownTitle
    ) {}

    record ParsedOperation(
            LocalDate date,
            String accountTitle,
            BigDecimal amount,
            BigDecimal quantity,
            OperationType type,
            String parentCategoryTitle,
            String subcategoryTitle,
            String comment,
            String counterparty
    ) {}

}
