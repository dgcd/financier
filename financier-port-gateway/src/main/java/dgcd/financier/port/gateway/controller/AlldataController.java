package dgcd.financier.port.gateway.controller;

import dgcd.financier.port.gateway.aspects.LogControllerData;
import dgcd.financier.port.gateway.service.AlldataService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

import static dgcd.financier.port.gateway.WebConstants.ALLDATA_ATTACHMENT_NAMING;
import static dgcd.financier.port.gateway.WebConstants.DATA_EXCEL_EXPORT_PATH;
import static dgcd.financier.port.gateway.WebConstants.X_APPLICATION_XLSX_VALUE;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequiredArgsConstructor
class AlldataController {

    private final AlldataService alldataService;


    @LogControllerData(logParams = false)
    @PostMapping(value = DATA_EXCEL_EXPORT_PATH)
    @Operation(summary = "Export all data from database to Excel sheet")
    public void exportAllData(HttpServletResponse response) {
        var contentField = String.format(ALLDATA_ATTACHMENT_NAMING, LocalDate.now());
        response.setHeader(CONTENT_DISPOSITION, contentField);
        response.setContentType(X_APPLICATION_XLSX_VALUE);
        try (var outputStream = response.getOutputStream()) {
            alldataService.exportAllDataToExcel(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @LogControllerData
//    @PostMapping(value = DATA_EXCEL_IMPORT_PATH, consumes = MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "Import all data from Excel sheet to empty database")
//    public CommonResponseDto importAllData(@Valid AlldataImportRequestDto dto) {
//        var payload = alldataService.importAllDataFromExcel(dto);
//        return CommonResponseDto.ok(payload);
//    }

}
