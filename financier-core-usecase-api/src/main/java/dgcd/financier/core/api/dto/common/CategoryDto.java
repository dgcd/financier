package dgcd.financier.core.api.dto.common;

public record CategoryDto(
        Long id,
        String title,
        Long parentId,
        String parentTitle
) {}
