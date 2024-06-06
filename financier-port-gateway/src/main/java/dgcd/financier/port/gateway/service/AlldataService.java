package dgcd.financier.port.gateway.service;

import dgcd.financier.core.usecase.api.AlldataExportUsecase;
import dgcd.financier.core.usecase.api.AlldataImportUsecase;
import dgcd.financier.core.usecase.api.dto.EmptyDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.port.gateway.dto.AlldataImportRequestDto;
import dgcd.financier.port.gateway.service.alldata.AlldataError;
import dgcd.financier.port.gateway.service.alldata.AlldataGenerateExcelService;
import dgcd.financier.port.gateway.service.alldata.AlldataParseExcelService;
import io.vavr.control.Either;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static dgcd.financier.core.usecase.api.dto.EmptyDto.EMPTY;
import static io.vavr.control.Either.left;

@Service
@RequiredArgsConstructor
public class AlldataService {

    private final AlldataExportUsecase alldataExportUsecase;
    private final AlldataImportUsecase alldataImportUsecase;

    private final AlldataGenerateExcelService alldataGenerateExcelService;
    private final AlldataParseExcelService alldataParseExcelService;


    @Transactional(readOnly = true)
    public void exportAllDataToExcel(ServletOutputStream outputStream) throws IOException {
        var alldataRows = alldataExportUsecase.execute().get();
        alldataGenerateExcelService.generateExcel(outputStream, alldataRows);
    }


    @Transactional
    public Either<CommonError, EmptyDto> importAllDataFromExcel(AlldataImportRequestDto dto) {
        try (var inputStream = dto.file().getInputStream()) {
            return alldataParseExcelService.parseDataFromExcel(inputStream)
                    .flatMap(alldataImportUsecase::execute);
        } catch (IOException e) {
            return left(AlldataError.of(e));
        }
    }

}
