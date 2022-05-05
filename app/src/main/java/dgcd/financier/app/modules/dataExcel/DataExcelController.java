package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.infrastructure.aspects.LogControllerData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static dgcd.financier.app.infrastructure.web.WebConstants.DATA_EXCEL_EXPORT_PATH;
import static dgcd.financier.app.infrastructure.web.WebConstants.X_APPLICATION_XLSX_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class DataExcelController {

    private final DataExcelExportService dataExcelExportService;

    @SneakyThrows
    @LogControllerData(logParams = false)
    @PostMapping(DATA_EXCEL_EXPORT_PATH)
    public void exportAllData(HttpServletResponse response) {
        response.setContentType(X_APPLICATION_XLSX_VALUE);
        var desposition = String.format("attachment;filename=financier_data_%s.xlsx", LocalDate.now());
        response.setHeader("Content-Disposition", desposition);
        try (var outputStream = response.getOutputStream()) {
            dataExcelExportService.exportAllDataToExcel(outputStream);
        }
    }

}
