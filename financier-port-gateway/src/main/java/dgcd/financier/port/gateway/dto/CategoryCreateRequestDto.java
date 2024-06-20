package dgcd.financier.port.gateway.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MIN_LENGTH;

public record CategoryCreateRequestDto(
        @NotNull(message = "Title can not be null")
        @Size(
                min = CATEGORY_TITLE_MIN_LENGTH,
                max = CATEGORY_TITLE_MAX_LENGTH,
                message = "Category title length must be " + CATEGORY_TITLE_MIN_LENGTH + ".." + CATEGORY_TITLE_MAX_LENGTH
        )
        String title,

        @Min(1)
        Long parentId
) {}
