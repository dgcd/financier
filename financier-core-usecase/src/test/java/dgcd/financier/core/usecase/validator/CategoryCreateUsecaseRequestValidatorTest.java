package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.usecase.CategoryCreateUsecase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryCreateUsecaseRequestValidatorTest {

    @Test
    void test_validate_nullParent_OK() {
        // given
        var request = new CategoryCreateUsecase.Request("accountTitle", null);

        // when
        CategoryCreateUsecaseRequestValidator.validate(request);
    }


    @Test
    void test_validate_nonNullParentIdentity_OK() {
        // given
        var request = new CategoryCreateUsecase.Request("accountTitle", 41L);

        // when
        CategoryCreateUsecaseRequestValidator.validate(request);
    }


    @Test
    void test_validate_nullRequest_ERROR() {
        // when
        assertThatThrownBy(() -> CategoryCreateUsecaseRequestValidator.validate(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Request can not be null");
    }


    @Test
    void test_validate_nullTitle_ERROR() {
        // given
        var request = new CategoryCreateUsecase.Request(null, 42L);

        // when
        assertThatThrownBy(() -> CategoryCreateUsecaseRequestValidator.validate(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Title can not be null");
    }

}
