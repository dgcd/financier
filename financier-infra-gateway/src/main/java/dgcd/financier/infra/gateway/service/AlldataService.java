package dgcd.financier.infra.gateway.service;

import dgcd.financier.core.usecase.AlldataExportUsecase;
import dgcd.financier.core.usecase.AlldataImportUsecase;
import dgcd.financier.core.usecase.AlldataUsecase;
import dgcd.financier.infra.gateway.dto.AlldataImportRequestDto;
import dgcd.financier.infra.gateway.dto.AlldataImportResponseDto;
import dgcd.financier.infra.gateway.service.alldata.AlldataGenerateExcelService;
import dgcd.financier.infra.gateway.service.alldata.AlldataParseExcelService;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlldataService {

    private final AlldataExportUsecase alldataExportUsecase;
    private final AlldataImportUsecase alldataImportUsecase;

    private final AlldataGenerateExcelService alldataGenerateExcelService;
    private final AlldataParseExcelService alldataParseExcelService;


    @Transactional(readOnly = true)
    public void exportAllDataToExcel(ServletOutputStream outputStream) throws IOException {
        var alldataRows = alldataExportUsecase.execute(null).getAlldataRows();
        alldataGenerateExcelService.generateExcel(outputStream, alldataRows);
    }


    @Transactional
    public AlldataImportResponseDto importAllDataFromExcel(AlldataImportRequestDto dto) {
        try (var inputStream = dto.file().getInputStream()) {
            var parsedData = alldataParseExcelService.parseDataFromExcel(inputStream);
            alldataImportUsecase.execute(new AlldataImportUsecase.Request(new AlldataUsecase.AlldataRows(
                    parsedData.accounts(),
                    parsedData.categories(),
                    parsedData.operations(),
                    parsedData.rates()
            )));
            return new AlldataImportResponseDto();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public record ParsedData(
            List<AlldataUsecase.AccountRow> accounts,
            List<AlldataUsecase.CategoryRow> categories,
            List<AlldataUsecase.OperationRow> operations,
            List<AlldataUsecase.RatesRow> rates
    ) {}

}
