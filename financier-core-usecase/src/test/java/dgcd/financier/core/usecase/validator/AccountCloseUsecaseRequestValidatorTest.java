package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.usecase.AccountCloseUsecase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountCloseUsecaseRequestValidatorTest {

    @Test
    void test_validate_OK() {
        // given
        var request = new AccountCloseUsecase.Request(42L);

        // when
        AccountCloseUsecaseRequestValidator.validate(request);
    }

    @Test
    void test_validate_nullRequest_ERROR() {
        // when
        assertThatThrownBy(() -> AccountCloseUsecaseRequestValidator.validate(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Request can not be null");
    }


    @Test
    void test_validate_nullIdentity_ERROR() {
        // given
        var request = new AccountCloseUsecase.Request(null);

        // when
        assertThatThrownBy(() -> AccountCloseUsecaseRequestValidator.validate(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Identity can not be null");
    }

}
