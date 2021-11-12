package dgcd.financier.app.dto.category;

public record CategoryCreateRequestDto(
        String title,
        Long parentId
) {
}
