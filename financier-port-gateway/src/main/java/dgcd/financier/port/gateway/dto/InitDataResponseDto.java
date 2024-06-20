package dgcd.financier.port.gateway.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record InitDataResponseDto(
        List<AccountResponseDto> accounts,
        List<CategoryResponseDto> categories,
        List<OperationResponseDto> operations,
        Map<String, BigDecimal> rates
) {}
