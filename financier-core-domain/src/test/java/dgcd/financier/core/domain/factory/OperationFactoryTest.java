package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.implementation.GeneralAccount;
import dgcd.financier.core.domain.implementation.GeneralCategory;
import dgcd.financier.core.domain.implementation.GeneralOperation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.OperationType.INCOME;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class OperationFactoryTest {

    @Test
    void test_makeNewOperation_OK() {
        // given
        var date = LocalDate.now();
        var account = new GeneralAccount(null, "account", EUR, ZERO, FALSE);
        var amount = new BigDecimal("99.95");
        var subcategory = new GeneralCategory(null, "subcat", new GeneralCategory(null, "parent", null));

        // when
        var operation = OperationFactory.makeExisting(
                42L,
                date,
                account,
                INCOME,
                amount,
                ONE,
                subcategory,
                "comment",
                "counterparty",
                FALSE,
                "corrId"
        );

        // then
        assertThat(operation.getIdentity()).isEqualTo(42L);
        assertThat(operation.getDate()).isEqualTo(date);
        assertThat(operation.getAccount()).isEqualTo(account);
        assertThat(operation.getType()).isEqualTo(INCOME);
        assertThat(operation.getAmount()).isEqualTo(amount);
        assertThat(operation.getQuantity()).isEqualTo(ONE);
        assertThat(operation.getSubcategory()).isEqualTo(subcategory);
        assertThat(operation.getComment()).isEqualTo("comment");
        assertThat(operation.getCounterparty()).isEqualTo("counterparty");
        assertThat(operation.getIsCanceled()).isEqualTo(FALSE);
        assertThat(operation.getCorrelationId()).isEqualTo("corrId");
    }


    @Test
    void test_makeNewOperationWithoutIdentity_OK() {
        // given
        var date = LocalDate.now();
        var account = new GeneralAccount(null, "account", EUR, ZERO, FALSE);
        var amount = new BigDecimal("99.95");
        var subcategory = new GeneralCategory(null, "subcat", new GeneralCategory(null, "parent", null));

        // when
        var operation = OperationFactory.makeNew(
                date,
                account,
                INCOME,
                amount,
                ONE,
                subcategory,
                "comment",
                "counterparty",
                "corrId"
        );

        // then
        assertThat(operation.getIdentity()).isNull();
        assertThat(operation.getDate()).isEqualTo(date);
        assertThat(operation.getAccount()).isEqualTo(account);
        assertThat(operation.getType()).isEqualTo(INCOME);
        assertThat(operation.getAmount()).isEqualTo(amount);
        assertThat(operation.getQuantity()).isEqualTo(ONE);
        assertThat(operation.getSubcategory()).isEqualTo(subcategory);
        assertThat(operation.getComment()).isEqualTo("comment");
        assertThat(operation.getCounterparty()).isEqualTo("counterparty");
        assertThat(operation.getIsCanceled()).isEqualTo(FALSE);
        assertThat(operation.getCorrelationId()).isEqualTo("corrId");
    }


    @Test
    void test_makeWithIsCanceledSet_OK() {
        // given
        var date = LocalDate.now();
        var account = new GeneralAccount(null, "account", EUR, ZERO, FALSE);
        var amount = new BigDecimal("99.95");
        var subcategory = new GeneralCategory(null, "subcat", new GeneralCategory(null, "parent", null));
        var operation = new GeneralOperation(
                null,
                date,
                account,
                INCOME,
                amount,
                ONE,
                subcategory,
                "comment",
                "counterparty",
                FALSE,
                "corrId"
        );

        var newBalance = account.getBalance().add(BigDecimal.valueOf(100));
        var newAccount = new GeneralAccount(null, "account", EUR, newBalance, FALSE);

        // when
        var newOperation = OperationFactory.makeWithIsCanceledSet(operation, newAccount);

        // then
        assertThat(newOperation.getIdentity()).isNull();
        assertThat(newOperation.getDate()).isEqualTo(date);
        assertThat(newOperation.getAccount().getBalance()).isEqualTo(newBalance);
        assertThat(newOperation.getType()).isEqualTo(INCOME);
        assertThat(newOperation.getAmount()).isEqualTo(amount);
        assertThat(newOperation.getQuantity()).isEqualTo(ONE);
        assertThat(newOperation.getSubcategory()).isEqualTo(subcategory);
        assertThat(newOperation.getComment()).isEqualTo("comment");
        assertThat(newOperation.getCounterparty()).isEqualTo("counterparty");
        assertThat(newOperation.getIsCanceled()).isEqualTo(TRUE);
        assertThat(newOperation.getCorrelationId()).isEqualTo("corrId");
    }

}
