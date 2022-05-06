package dgcd.financier.app.modules.initData;

import dgcd.financier.app.infrastructure.actuator.TechInfoContributor;
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
class InitDataService {

    private final AccountsDaoService accountsDaoService;
    private final CategoriesDaoService categoriesDaoService;
    private final OperationsDaoService operationsDaoService;
    private final ValuteService valuteService;
    private final TechInfoContributor techInfoContributor;

    @Transactional(readOnly = true)
    public InitDataResponseDto getInitData() {
        return new InitDataResponseDto(
                Map.of(
                        "USD", valuteService.getRateUsd(),
                        "EUR", valuteService.getRateEur()
                ),
                accountsDaoService.findAll().stream().map(AccountResponseDto::of).toList(),
                categoriesDaoService.findAll().stream().map(CategoryResponseDto::of).toList(),
                operationsDaoService.findAll().stream().map(OperationResponseDto::of).toList(),
                techInfoContributor.getTechInfo()
        );
    }

}
