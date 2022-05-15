package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.infrastructure.aspects.LogControllerData;
import dgcd.financier.app.infrastructure.dto.CommonResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static dgcd.financier.app.infrastructure.web.WebConstants.DATA_EXCEL_EXPORT_PATH;
import static dgcd.financier.app.infrastructure.web.WebConstants.DATA_EXCEL_IMPORT_PATH;
import static dgcd.financier.app.infrastructure.web.WebConstants.X_APPLICATION_XLSX_VALUE;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
class DataExcelController {

    private final DataExcelService dataExcelService;


    @SneakyThrows
    @LogControllerData(logParams = false)
    @PostMapping(value = DATA_EXCEL_EXPORT_PATH, consumes = APPLICATION_JSON_VALUE)
    public void exportAllData(HttpServletResponse response) {
        response.setContentType(X_APPLICATION_XLSX_VALUE);
        response.setHeader(
                CONTENT_DISPOSITION,
                String.format("attachment;filename=financier_data_%s.xlsx", LocalDate.now())
        );
        try (var outputStream = response.getOutputStream()) {
            dataExcelService.exportAllDataToExcel(outputStream);
        }
    }


    @LogControllerData
    @PostMapping(value = DATA_EXCEL_IMPORT_PATH, consumes = MULTIPART_FORM_DATA_VALUE)
    public CommonResponseDto importAllData(@Valid ImportDataRequestDto dto) {
        var payload = dataExcelService.importAllDataFromExcel(dto);
        return CommonResponseDto.ok(payload);
    }

}
