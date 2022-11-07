package dgcd.financier.modules.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequestDto(
        @NotNull(message = "Title can not be null")
        @Size(min = 1, max = 30, message = "Category title length must be 1..30")
        String title,

        Long parentId
) {}
