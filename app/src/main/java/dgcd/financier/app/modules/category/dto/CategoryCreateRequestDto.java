package dgcd.financier.app.modules.category.dto;

import dgcd.financier.app.modules.category.Category;

public record CategoryCreateRequestDto(
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
