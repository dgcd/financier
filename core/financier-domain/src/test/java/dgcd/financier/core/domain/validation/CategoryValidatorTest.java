package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.exception.IllegalCategoryTitleException;
import dgcd.financier.core.domain.exception.IllegalIdentityException;
import dgcd.financier.core.domain.implementation.GeneralCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryValidatorTest {

    @Test
    void test_all_is_OK() {
        new GeneralCategory(null, "title", null);
    }

    @Test
    void test_zeroIdentity_ERROR() {
        assertThatThrownBy(() -> new GeneralCategory(0L, null, null))
                .isInstanceOf(IllegalIdentityException.class)
                .hasMessage("Identity must be greater then 0 but was: 0");
    }

    @Test
    void test_negativeIdentity_ERROR() {
        assertThatThrownBy(() -> new GeneralCategory(-42L, null, null))
                .isInstanceOf(IllegalIdentityException.class)
                .hasMessage("Identity must be greater then 0 but was: -42");
    }

    @Test
    void test_null_title_ERROR() {
        assertThatThrownBy(() -> new GeneralCategory(null, null, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Title can not be null");
    }

    @Test
    void test_short_title_ERROR() {
        assertThatThrownBy(() -> new GeneralCategory(null, "1", null))
                .isInstanceOf(IllegalCategoryTitleException.class)
                .hasMessage("Category title length must be from 2 to 20 but was: 1");
    }

    @Test
    void test_long_title_ERROR() {
        assertThatThrownBy(() -> new GeneralCategory(null, "12345678901234567890_", null))
                .isInstanceOf(IllegalCategoryTitleException.class)
                .hasMessage("Category title length must be from 2 to 20 but was: 21");
    }

}
