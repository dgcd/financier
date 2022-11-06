package dgcd.financier.modules.initData;

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
