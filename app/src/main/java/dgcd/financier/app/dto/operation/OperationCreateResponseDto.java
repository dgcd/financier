package dgcd.financier.app.dto.operation;

import dgcd.financier.app.dto.account.AccountResponseDto;

public record OperationCreateResponseDto(
        OperationResponseDto operation,
        AccountResponseDto account
) {}
