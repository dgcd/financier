package dgcd.financier.infra.gateway.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AlldataImportRequestDto(
        @NotNull MultipartFile file
) {}
