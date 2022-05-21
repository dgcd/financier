package dgcd.financier.app.modules.dataExcel;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ImportDataRequestDto(
        @NotNull MultipartFile file
) {}
