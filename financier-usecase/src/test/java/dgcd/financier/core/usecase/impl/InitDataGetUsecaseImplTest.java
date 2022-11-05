package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.domain.factory.CategoryFactory;
import dgcd.financier.core.domain.factory.OperationFactory;
import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.service.RatesService;
import dgcd.financier.core.usecase.port.service.TechInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.RUB;
import static dgcd.financier.core.domain.Currency.USD;
import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.OperationType.EXPENSE;
import static dgcd.financier.core.domain.OperationType.INCOME;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InitDataGetUsecaseImplTest {

    @Mock
    private AccountsRepository accountsRepository;
    @Mock
    private CategoriesRepository categoriesRepository;
    @Mock
    private OperationsRepository operationsRepository;
    @Mock
    private RatesService ratesService;
    @Mock
    private TechInfoService techInfoService;

    @InjectMocks
    private InitDataGetUsecaseImpl initDataGetUsecase;

    @Test
    void test_executeUsecase_OK() {
        // given
        var request = new InitDataGetUsecase.Request();

        // and
        var account = AccountFactory.makeNew("account", RUB);
        var accounts = List.of(account);

        var parentCategory = CategoryFactory.makeNewParent("parent");
        var subcategory1 = CategoryFactory.makeNewChild("subcat1", parentCategory);
        var subcategory2 = CategoryFactory.makeNewChild("subcat2", parentCategory);
        var caregories = List.of(parentCategory, subcategory2, subcategory1);

        var date = LocalDate.now();
        var operation1 = OperationFactory.makeNew(date, account, EXPENSE, new BigDecimal("10000"), ONE, subcategory1, "comment1", "counterparty1", null);
        var operation2 = OperationFactory.makeNew(date, account, INCOME, new BigDecimal("2300.5"), ONE, subcategory1, "comment2", null, null);
        var operation3 = OperationFactory.makeNew(date, account, BASE, new BigDecimal("10034099"), ONE, subcategory2, null, null, null);
        var operations = List.of(operation1, operation2, operation3);

        BigDecimal usdRate = BigDecimal.valueOf(84);
        BigDecimal eurRate = BigDecimal.valueOf(94);
        var rates = Map.of(
                USD.name(), usdRate,
                EUR.name(), eurRate
        );

        var version = "42 ".concat(UUID.randomUUID().toString());
        var techInfo = Map.of("javaVersion", version);

        // and
        when(accountsRepository.findAll()).thenReturn(accounts);
        when(categoriesRepository.findAll()).thenReturn(caregories);
        when(operationsRepository.findAllNotCanceled()).thenReturn(operations);
        when(ratesService.getRates()).thenReturn(rates);
        when(techInfoService.getTechInfo()).thenReturn(techInfo);

        // when
        var response = initDataGetUsecase.execute(request);

        // then
        assertThat(response.getAccounts()).hasSize(1);
        assertThat(response.getAccounts()).first().isEqualTo(account);

        assertThat(response.getCategories()).hasSize(3);
        assertThat(response.getCategories()).allMatch(cat -> cat.isParent() || cat.getParent().equals(parentCategory));

        assertThat(response.getOperations()).hasSize(3);
        assertThat(response.getOperations()).allMatch(op -> op.getDate().equals(date) && op.getAccount().equals(account));

        assertThat(response.getRates()).hasSize(2);
        assertThat(response.getRates()).extracting(USD.name()).isEqualTo(usdRate);
        assertThat(response.getRates()).extracting(EUR.name()).isEqualTo(eurRate);

        assertThat(response.getTechInfo()).hasSize(1);
        assertThat(response.getTechInfo()).extracting("javaVersion").isEqualTo(version);
    }

}
