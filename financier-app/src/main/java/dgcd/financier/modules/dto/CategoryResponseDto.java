package dgcd.financier.modules.dto;

public record CategoryResponseDto(
        Long id,
        String title,
        Long parentId,
        String parentTitle
) {}
