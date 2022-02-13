package dgcd.financier.app.modules.operation;

import dgcd.financier.app.modules.account.AccountsDaoService;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.CategoriesDaoService;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationCreateResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperationsService {

    private final OperationsDaoService operationsDaoService;
    private final AccountsDaoService accountsDaoService;
    private final CategoriesDaoService categoriesDaoService;

    @Transactional
    public OperationCreateResponseDto createOperation(OperationCreateRequestDto dto) {
        var account = accountsDaoService.findByIdOrElseThrow(dto.accountId());
//        var category = BASE.equals(dto.operationType()) ?
//                null :
//                categoriesDaoService.findByIdOrElseThrow(dto.categoryId());

        var operation = dto.makeOperation();
        operation.setAccount(account);
        operation.setCategory(null);
        account.setBalance(account.getBalance().add(operation.getAmount()));

        operation = operationsDaoService.save(operation);

        return new OperationCreateResponseDto(
                OperationResponseDto.of(operation),
                AccountResponseDto.of(account)
        );
    }

}
