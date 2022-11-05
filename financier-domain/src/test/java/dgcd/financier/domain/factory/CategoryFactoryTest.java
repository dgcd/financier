package dgcd.financier.domain.factory;

import dgcd.financier.domain.implementation.GeneralCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryFactoryTest {

    @Test
    void test_makeNewParent_OK() {
        // when
        var parent = CategoryFactory.makeNewParent("title");

        // than
        assertThat(parent.getId()).isNull();
        assertThat(parent.getTitle()).isEqualTo("title");
        assertThat(parent.getParent()).isNull();
    }


    @Test
    void test_makeNewChild_OK() {
        // given
        var parent = new GeneralCategory(null, "parent", null);

        // when
        var child = CategoryFactory.makeNewChild("child", parent);

        // than
        assertThat(child.getId()).isNull();
        assertThat(child.getTitle()).isEqualTo("child");
        assertThat(child.getParent()).isEqualTo(parent);
    }


    @Test
    void test_makeNewChild_null_parent_ERROR() {
        assertThatThrownBy(() -> CategoryFactory.makeNewChild("child", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Parent category can not be null");
    }

}