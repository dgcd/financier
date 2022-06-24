package dgcd.financier.modules.initData;

import dgcd.financier.modules.valute.ValuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
class InitDataService {

//    private final AccountsDaoService accountsDaoService;
//    private final CategoriesDaoService categoriesDaoService;
//    private final OperationsDaoService operationsDaoService;
    private final ValuteService valuteService;
//    private final TechInfoContributor techInfoContributor;

    @Transactional(readOnly = true)
    public InitDataResponseDto getInitData() {
        return new InitDataResponseDto(
                Map.of(
                        "USD", valuteService.getRateUsd(),
                        "EUR", valuteService.getRateEur()
                ),
                List.of(),
                List.of(),
                List.of()
//                accountsDaoService.findAll().stream().map(AccountResponseDto::of).toList(),
//                categoriesDaoService.findAll().stream().map(CategoryResponseDto::of).toList(),
//                operationsDaoService.findAll().stream().map(OperationResponseDto::of).toList(),
//                techInfoContributor.getTechInfo()
        );
    }

}
