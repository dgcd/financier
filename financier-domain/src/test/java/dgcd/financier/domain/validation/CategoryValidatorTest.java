package dgcd.financier.domain.validation;

import dgcd.financier.domain.exception.IllegalCategoryTitleException;
import dgcd.financier.domain.implementation.GeneralCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryValidatorTest {

    @Test
    void test_all_is_OK() {
        new GeneralCategory(null, "title", null);
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
