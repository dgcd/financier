package dgcd.financier.infra.gateway.controller;

import dgcd.financier.infra.gateway.aspects.LogControllerData;
import dgcd.financier.infra.gateway.dto.AlldataImportRequestDto;
import dgcd.financier.infra.gateway.dto.CommonResponseDto;
import dgcd.financier.infra.gateway.service.AlldataService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

import static dgcd.financier.infra.gateway.WebConstants.DATA_EXCEL_EXPORT_PATH;
import static dgcd.financier.infra.gateway.WebConstants.DATA_EXCEL_IMPORT_PATH;
import static dgcd.financier.infra.gateway.WebConstants.X_APPLICATION_XLSX_VALUE;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
class AlldataController {

    private final AlldataService alldataService;


    @LogControllerData(logParams = false)
    @PostMapping(value = DATA_EXCEL_EXPORT_PATH, consumes = APPLICATION_JSON_VALUE)
    public void exportAllData(HttpServletResponse response) {
        response.setContentType(X_APPLICATION_XLSX_VALUE);
        response.setHeader(
                CONTENT_DISPOSITION,
                String.format("attachment;filename=financier_data_%s.xlsx", LocalDate.now())
        );
        try (var outputStream = response.getOutputStream()) {
            alldataService.exportAllDataToExcel(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @LogControllerData
    @PostMapping(value = DATA_EXCEL_IMPORT_PATH, consumes = MULTIPART_FORM_DATA_VALUE)
    public CommonResponseDto importAllData(@Valid AlldataImportRequestDto dto) {
        var payload = alldataService.importAllDataFromExcel(dto);
        return CommonResponseDto.ok(payload);
    }

}
