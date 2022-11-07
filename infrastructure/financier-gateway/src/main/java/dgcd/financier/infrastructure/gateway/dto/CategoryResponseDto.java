package dgcd.financier.infrastructure.gateway.dto;

public record CategoryResponseDto(
        Long id,
        String title,
        Long parentId,
        String parentTitle
) {}
