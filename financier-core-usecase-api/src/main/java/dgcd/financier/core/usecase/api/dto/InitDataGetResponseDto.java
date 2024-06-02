package dgcd.financier.core.usecase.api.dto;

import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.core.usecase.api.dto.common.CategoryDto;
import dgcd.financier.core.usecase.api.dto.common.OperationDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record InitDataGetResponseDto(
        List<AccountDto> accounts,
        List<CategoryDto> categories,
        List<OperationDto> operations,
        Map<String, BigDecimal> rates
) {}
