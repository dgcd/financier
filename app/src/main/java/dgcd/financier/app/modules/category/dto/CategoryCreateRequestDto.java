package dgcd.financier.app.modules.category.dto;

import dgcd.financier.app.modules.category.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequestDto(
        @NotNull(message = "Title can not be null")
        @Size(min = 1, max = 30, message = "Category title length must be 1..30")
        String title,

        Long parentId
) {

    public Category makeCategory() {
        return new Category(
                null,
                this.title,
                null
        );
    }

}
