package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.exception.IllegalAccountTitleException;
import dgcd.financier.core.domain.exception.IllegalIdentityException;
import dgcd.financier.core.domain.implementation.GeneralAccount;
import org.junit.jupiter.api.Test;

import static dgcd.financier.core.domain.Currency.EUR;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountValidatorTest {

    @Test
    void test_all_is_OK() {
        new GeneralAccount(null, "title", EUR, ZERO, FALSE);
    }

    @Test
    void test_null_title_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(null, null, EUR, ZERO, FALSE))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Title can not be null");
    }

    @Test
    void test_zeroIdentity_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(0L, null, EUR, ZERO, FALSE))
                .isInstanceOf(IllegalIdentityException.class)
                .hasMessage("Identity must be greater then 0 but was: 0");
    }

    @Test
    void test_negativeIdentity_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(-42L, null, EUR, ZERO, FALSE))
                .isInstanceOf(IllegalIdentityException.class)
                .hasMessage("Identity must be greater then 0 but was: -42");
    }

    @Test
    void test_short_title_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(null, "1", EUR, ZERO, FALSE))
                .isInstanceOf(IllegalAccountTitleException.class)
                .hasMessage("Account title length must be from 5 to 40 but was: 1");
    }

    @Test
    void test_long_title_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(null, "1234567890123456789012345678901234567890_", EUR, ZERO, FALSE))
                .isInstanceOf(IllegalAccountTitleException.class)
                .hasMessage("Account title length must be from 5 to 40 but was: 41");
    }

    @Test
    void test_null_currency_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(null, "title", null, ZERO, FALSE))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Currency can not be null");
    }

    @Test
    void test_null_balance_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(null, "title", EUR, null, FALSE))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Balance can not be null");
    }

    @Test
    void test_null_isClosed_status_ERROR() {
        assertThatThrownBy(() -> new GeneralAccount(null, "title", EUR, ZERO, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("IsClosed status can not be null");
    }

}
