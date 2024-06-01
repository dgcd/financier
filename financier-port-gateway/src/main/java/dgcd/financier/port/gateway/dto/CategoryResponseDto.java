package dgcd.financier.port.gateway.dto;

public record CategoryResponseDto(
        Long id,
        String title,
        Long parentId,
        String parentTitle
) {}
