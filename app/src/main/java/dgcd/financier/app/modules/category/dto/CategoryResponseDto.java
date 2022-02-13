package dgcd.financier.app.modules.category.dto;

import dgcd.financier.app.modules.category.Category;

import static java.util.Objects.nonNull;

public record CategoryResponseDto(
        Long id,
        String title,
        Long parentId
) {

    public static CategoryResponseDto of(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getTitle(),
                nonNull(category.getParent()) ? category.getParent().getId() : null
        );
    }

}
