package dgcd.financier.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CommonIdDto(
        @NotNull(message = "Id can not be null")
        Long id
) {}
