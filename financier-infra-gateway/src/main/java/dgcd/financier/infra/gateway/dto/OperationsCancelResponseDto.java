package dgcd.financier.infra.gateway.dto;

import java.util.List;

public record OperationsCancelResponseDto(
        List<Long> canceledOperationsIds,
        List<AccountResponseDto> updatedAccounts
) {}
