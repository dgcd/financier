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
        var parent = category.getParent();
        var hasParent = nonNull(parent);
        return new CategoryResponseDto(
                category.getId(),
                category.getTitle(),
                hasParent ? parent.getId() : null,
                hasParent ? parent.getTitle() : null
        );
    }

}
