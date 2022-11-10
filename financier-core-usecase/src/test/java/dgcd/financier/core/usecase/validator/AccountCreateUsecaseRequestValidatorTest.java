package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.usecase.AccountCreateUsecase;
import org.junit.jupiter.api.Test;

import static dgcd.financier.core.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountCreateUsecaseRequestValidatorTest {

    @Test
    void test_validate_OK() {
        // given
        var request = new AccountCreateUsecase.Request("accountTitle", USD);

        // when
        AccountCreateUsecaseRequestValidator.validate(request);
    }

    @Test
    void test_validate_nullRequest_ERROR() {
        // when
        assertThatThrownBy(() -> AccountCreateUsecaseRequestValidator.validate(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Request can not be null");
    }


    @Test
    void test_validate_nullTitle_ERROR() {
        // given
        var request = new AccountCreateUsecase.Request(null, USD);

        // when
        assertThatThrownBy(() -> AccountCreateUsecaseRequestValidator.validate(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Title can not be null");
    }


    @Test
    void test_validate_nullCurrency_ERROR() {
        // given
        var request = new AccountCreateUsecase.Request("title", null);

        // when
        assertThatThrownBy(() -> AccountCreateUsecaseRequestValidator.validate(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Currency can not be null");
    }

}
