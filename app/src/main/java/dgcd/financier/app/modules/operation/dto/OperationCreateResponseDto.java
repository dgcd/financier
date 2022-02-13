package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.modules.account.dto.AccountResponseDto;

public record OperationCreateResponseDto(
        OperationResponseDto operation,
        AccountResponseDto account
) {}
