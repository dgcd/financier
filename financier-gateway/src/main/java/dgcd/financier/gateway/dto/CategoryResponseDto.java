package dgcd.financier.gateway.dto;

public record CategoryResponseDto(
        Long id,
        String title,
        Long parentId,
        String parentTitle
) {}
