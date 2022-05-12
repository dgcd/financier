package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.dto.CategoryResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;

import java.util.List;

public record ImportDataResponseDto(
        List<AccountResponseDto> accounts,
        List<CategoryResponseDto> categories,
        List<OperationResponseDto> operations
) {}
