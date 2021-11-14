package dgcd.financier.app.dto.init;

import dgcd.financier.app.dto.account.AccountResponseDto;
import dgcd.financier.app.dto.category.CategoryResponseDto;
import dgcd.financier.app.dto.operation.OperationResponseDto;

import java.util.List;

public record InitResponseDto(
        List<AccountResponseDto> accounts,
        List<CategoryResponseDto> categories,
        List<OperationResponseDto> operations
) {}
