package dgcd.financier.modules.initData;

import dgcd.financier.modules.category.dto.CategoryResponseDto;
import dgcd.financier.modules.operation.dto.OperationResponseDto;
import dgcd.financier.usecase.dto.AccountResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

record InitDataResponseDto(
        Map<String, BigDecimal> rates,
        List<AccountResponseDto> accounts,
        List<CategoryResponseDto> categories,
        List<OperationResponseDto> operations
//        ,
//        Map<String, String> techInfo
) {}
