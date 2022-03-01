package dgcd.financier.app.modules.init;

import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.dto.CategoryResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record InitResponseDto(
        Map<String, BigDecimal> rates,
        List<AccountResponseDto> accounts,
        List<CategoryResponseDto> categories,
        List<OperationResponseDto> operations
) {}
