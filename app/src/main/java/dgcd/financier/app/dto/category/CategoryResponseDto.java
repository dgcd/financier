package dgcd.financier.app.dto.category;

import dgcd.financier.app.domain.model.Category;

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
