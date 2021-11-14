package dgcd.financier.app.service;

import dgcd.financier.app.dto.account.AccountResponseDto;
import dgcd.financier.app.dto.category.CategoryResponseDto;
import dgcd.financier.app.dto.init.InitResponseDto;
import dgcd.financier.app.dto.operation.OperationResponseDto;
import dgcd.financier.app.service.dao.AccountsDaoService;
import dgcd.financier.app.service.dao.CategoriesDaoService;
import dgcd.financier.app.service.dao.OperationsDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitService {

    private final AccountsDaoService accountsDaoService;
    private final CategoriesDaoService categoriesDaoService;
    private final OperationsDaoService operationsDaoService;

    @Transactional(readOnly = true)
    public InitResponseDto getInitData() {
        return new InitResponseDto(
                accountsDaoService.findAll().stream().map(AccountResponseDto::of).toList(),
                categoriesDaoService.findAll().stream().map(CategoryResponseDto::of).toList(),
                operationsDaoService.findAll().stream().map(OperationResponseDto::of).toList()
        );
    }

}
