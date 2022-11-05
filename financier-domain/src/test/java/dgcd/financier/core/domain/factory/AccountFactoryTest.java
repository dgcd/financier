package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.implementation.GeneralAccount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static dgcd.financier.core.domain.Currency.RUB;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class AccountFactoryTest {

    @Test
    void test_makeNew_OK() {
        // when
        var account = AccountFactory.makeNew("title123", RUB);

        // then
        assertThat(account.getId()).isNull();
        assertThat(account.getTitle()).isEqualTo("title123");
        assertThat(account.getCurrency()).isEqualTo(RUB);
        assertThat(account.getBalance()).isEqualTo(ZERO);
        assertThat(account.getIsClosed()).isFalse();
    }


    @Test
    void test_makeWithNewBalance_OK() {
        // given
        var srcAccount = new GeneralAccount(null, "title123", RUB, ZERO, FALSE);
        var newBalance = new BigDecimal("123.45");

        // when
        var newAccount = AccountFactory.makeWithNewBalance(srcAccount, newBalance);

        // then
        assertThat(newAccount.getId()).isNull();
        assertThat(newAccount.getTitle()).isEqualTo("title123");
        assertThat(newAccount.getCurrency()).isEqualTo(RUB);
        assertThat(newAccount.getBalance()).isEqualTo(newBalance);
        assertThat(newAccount.getIsClosed()).isFalse();
    }


    @Test
    void test_makeWithSetClosed_OK() {
        // given
        var srcAccount = new GeneralAccount(42L, "title123", RUB, ZERO, FALSE);

        // when
        var newAccount = AccountFactory.makeWithSetClosed(srcAccount);

        // then
        assertThat(newAccount.getId()).isEqualTo(42L);
        assertThat(newAccount.getTitle()).isEqualTo("title123");
        assertThat(newAccount.getCurrency()).isEqualTo(RUB);
        assertThat(newAccount.getBalance()).isEqualTo(ZERO);
        assertThat(newAccount.getIsClosed()).isTrue();
    }

}
