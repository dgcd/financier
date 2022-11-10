package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.implementation.GeneralCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryFactoryTest {

    @Test
    void test_makeExistingParent_OK() {
        // when
        var parent = CategoryFactory.makeExistingParent(42L, "title", null);

        // than
        assertThat(parent.getIdentity()).isEqualTo(42L);
        assertThat(parent.getTitle()).isEqualTo("title");
        assertThat(parent.getParent()).isNull();
    }


    @Test
    void test_makeExistingParentWithParent_EROR() {
        // given
        var wrongParent = CategoryFactory.makeNewParent("parent");

        // when

        assertThatThrownBy(() -> CategoryFactory.makeExistingParent(42L, "title", wrongParent))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parent category can not have parent");
    }


    @Test
    void test_makeNewParent_OK() {
        // when
        var parent = CategoryFactory.makeNewParent("title");

        // than
        assertThat(parent.getIdentity()).isNull();
        assertThat(parent.getTitle()).isEqualTo("title");
        assertThat(parent.getParent()).isNull();
    }


    @Test
    void test_makeNewChild_OK() {
        // given
        var parent = new GeneralCategory(null, "parent", null);

        // when
        var child = CategoryFactory.makeExistingSubcategory(42L, "child", parent);

        // than
        assertThat(child.getIdentity()).isEqualTo(42L);
        assertThat(child.getTitle()).isEqualTo("child");
        assertThat(child.getParent()).isEqualTo(parent);
    }

    @Test
    void test_makeNewChildWithoutIdentity_OK() {
        // given
        var parent = new GeneralCategory(null, "parent", null);

        // when
        var child = CategoryFactory.makeNewSubcategory("child", parent);

        // than
        assertThat(child.getIdentity()).isNull();
        assertThat(child.getTitle()).isEqualTo("child");
        assertThat(child.getParent()).isEqualTo(parent);
    }


    @Test
    void test_makeNewChild_null_parent_ERROR() {
        assertThatThrownBy(() -> CategoryFactory.makeExistingSubcategory(42L, "child", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Parent category can not be null");
    }

}