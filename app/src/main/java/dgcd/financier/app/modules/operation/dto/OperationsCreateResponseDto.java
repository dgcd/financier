package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.modules.account.dto.AccountResponseDto;

import java.util.List;

public record OperationsCreateResponseDto(
        List<OperationResponseDto> newOperations,
        List<AccountResponseDto> updatedAccounts
) {}
