package dgcd.financier.port.gateway.dto;

import java.util.List;

public record OperationsCreateResponseDto(
        List<OperationResponseDto> newOperations,
        List<AccountResponseDto> updatedAccounts
) {}
