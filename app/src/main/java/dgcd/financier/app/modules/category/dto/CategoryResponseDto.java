package dgcd.financier.app.modules.category.dto;

import dgcd.financier.app.modules.category.Category;

import static java.util.Objects.nonNull;

public record CategoryResponseDto(
        Long id,
        String title,
        Long parentId,
        String parentTitle
) {

    public static CategoryResponseDto of(Category category) {
        boolean hasParent = nonNull(category.getParent());
        return new CategoryResponseDto(
                category.getId(),
                category.getTitle(),
                hasParent ? category.getParent().getId() : null,
                hasParent ? category.getParent().getTitle() : null
        );
    }

}
