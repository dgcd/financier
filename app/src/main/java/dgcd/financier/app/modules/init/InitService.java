package dgcd.financier.app.modules.init;

import dgcd.financier.app.modules.account.AccountsDaoService;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.CategoriesDaoService;
import dgcd.financier.app.modules.category.dto.CategoryResponseDto;
import dgcd.financier.app.modules.operation.OperationsDaoService;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;
import dgcd.financier.app.modules.valute.ValuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class InitService {

    private final AccountsDaoService accountsDaoService;
    private final CategoriesDaoService categoriesDaoService;
    private final OperationsDaoService operationsDaoService;
    private final ValuteService valuteService;

    @Transactional(readOnly = true)
    public InitResponseDto getInitData() {
        return new InitResponseDto(
                Map.of(
                        "USD", valuteService.getRateUsd(),
                        "EUR", valuteService.getRateEur()
                ),
                accountsDaoService.findAll().stream().map(AccountResponseDto::of).toList(),
                categoriesDaoService.findAll().stream().map(CategoryResponseDto::of).toList(),
                operationsDaoService.findAll().stream().map(OperationResponseDto::of).toList()
        );
    }

}
