package dgcd.financier.port.gateway.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CommonIdDto(
        @NotNull(message = "Id can not be null")
        @Min(1)
        Long id
) {}
