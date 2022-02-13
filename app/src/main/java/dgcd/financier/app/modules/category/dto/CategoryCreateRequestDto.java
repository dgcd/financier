package dgcd.financier.app.modules.category.dto;

public record CategoryCreateRequestDto(
        String title,
        Long parentId
) {
}
