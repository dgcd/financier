package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.modules.account.dto.AccountResponseDto;

import java.util.List;

public record OperationsCancelResponseDto(
        List<Long> canceledOperationsIds,
        List<AccountResponseDto> updatedAccounts
) {}
