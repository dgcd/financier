package dgcd.financier.app.modules.operation;

import dgcd.financier.app.modules.account.AccountsDaoService;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationsCreateResponseDto;
import dgcd.financier.app.modules.operation.exceptions.OperationCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.math.BigDecimal.ONE;

@Service
@RequiredArgsConstructor
public class OperationsFacilityService {

    private final OperationsDaoService operationsDaoService;
    private final AccountsDaoService accountsDaoService;

    @Transactional
    public OperationsCreateResponseDto createOperation(OperationCreateRequestDto dto) {
        var account = accountsDaoService.findByIdOrElseThrow(dto.accountId());
        var operation = dto.makeOperation();
        operation.setAccount(account);
        account.setBalance(account.getBalance().add(operation.getAmount()));

        operation = operationsDaoService.save(operation);

        return new OperationsCreateResponseDto(
                List.of(OperationResponseDto.of(operation)),
                List.of(AccountResponseDto.of(account))
        );
    }

    @Transactional
    public OperationsCreateResponseDto createTransfert(OperationCreateRequestDto dto) {
        var accountFrom = accountsDaoService.findByIdOrElseThrow(dto.accountId());
        var accountTo = accountsDaoService.findByIdOrElseThrow(dto.accountToId());
        if (!accountFrom.getCurrency().equals(accountTo.getCurrency())) {
            throw new OperationCreateException("Account from and account to must have the same currency");
        }

        var operationFrom = dto.makeOperation();
        operationFrom.setAmount(operationFrom.getAmount().negate());
        operationFrom.setAccount(accountFrom);
        operationFrom.setQuantity(ONE);
        accountFrom.setBalance(accountFrom.getBalance().add(operationFrom.getAmount()));

        var operationTo = dto.makeOperation();
        operationTo.setAmount(operationTo.getAmount());
        operationTo.setAccount(accountTo);
        operationTo.setQuantity(ONE);
        accountTo.setBalance(accountTo.getBalance().add(operationTo.getAmount()));

        operationFrom = operationsDaoService.save(operationFrom);
        operationTo = operationsDaoService.save(operationTo);

        return new OperationsCreateResponseDto(
                List.of(OperationResponseDto.of(operationFrom), OperationResponseDto.of(operationTo)),
                List.of(AccountResponseDto.of(accountFrom), AccountResponseDto.of(accountTo))
        );
    }

}
