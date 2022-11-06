package dgcd.financier.modules.initData;

import dgcd.financier.modules.dto.AccountResponseDto;
import dgcd.financier.modules.dto.CategoryResponseDto;
import dgcd.financier.modules.dto.OperationResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

record InitDataResponseDto(
        List<AccountResponseDto> accounts,
        List<CategoryResponseDto> categories,
        List<OperationResponseDto> operations,
        Map<String, BigDecimal> rates,
        Map<String, String> techInfo
) {}
