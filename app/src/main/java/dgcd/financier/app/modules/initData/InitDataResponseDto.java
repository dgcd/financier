package dgcd.financier.app.modules.initData;

import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.dto.CategoryResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

record InitDataResponseDto(
        Map<String, BigDecimal> rates,
        List<AccountResponseDto> accounts,
        List<CategoryResponseDto> categories,
        List<OperationResponseDto> operations,
        Map<String, String> techInfo
) {}
