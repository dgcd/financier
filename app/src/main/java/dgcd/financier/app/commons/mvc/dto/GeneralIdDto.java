package dgcd.financier.app.commons.mvc.dto;

import jakarta.validation.constraints.NotNull;

public record GeneralIdDto(
        @NotNull(message = "Id can not be null") Long id
) {}
