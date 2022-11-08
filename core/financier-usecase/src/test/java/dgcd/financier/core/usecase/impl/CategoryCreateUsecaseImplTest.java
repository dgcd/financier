package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.usecase.CategoryCreateUsecase;
import dgcd.financier.core.usecase.exception.CategoryCanNotBeParentException;
import dgcd.financier.core.usecase.exception.CategoryParentNotFoundException;
import dgcd.financier.core.usecase.exception.CategoryWithTitleAlreadyExistsException;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static dgcd.financier.core.domain.factory.CategoryFactory.makeExistingParent;
import static dgcd.financier.core.domain.factory.CategoryFactory.makeExistingSubcategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryCreateUsecaseImplTest {

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoryCreateUsecaseImpl categoryCreateUsecase;


    @Test
    void test_execute_createParent_OK() {
        // given
        var request = new CategoryCreateUsecase.Request("parent", null);

        // and
        when(categoriesRepository.findAllByTitle(any()))
                .thenReturn(List.of(
                        makeExistingSubcategory(324L, "parent", makeExistingParent(213L, "otherParent", null))
                ));
        when(categoriesRepository.save(any()))
                .thenReturn(makeExistingParent(42L, "parent", null));

        // when
        var response = categoryCreateUsecase.execute(request);

        // then
        verify(categoriesRepository, times(1)).findAllByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(categoriesRepository).findAllByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("parent");

        verify(categoriesRepository, never()).findById(any());

        verify(categoriesRepository, times(1)).save(any());
        var categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoriesRepository).save(categoryCaptor.capture());
        var capturedCategory = categoryCaptor.getValue();
        assertThat(capturedCategory.getIdentity()).isNull();
        assertThat(capturedCategory.getTitle()).isEqualTo("parent");
        assertThat(capturedCategory.getParent()).isNull();

        var category = response.getCategory();
        assertThat(category.getIdentity()).isEqualTo(42L);
        assertThat(category.getTitle()).isEqualTo("parent");
        assertThat(category.getParent()).isNull();
    }


    @Test
    void test_execute_createParent_dupTitle_ERROR() {
        // given
        var request = new CategoryCreateUsecase.Request("parent", null);

        // and
        when(categoriesRepository.findAllByTitle(any()))
                .thenReturn(List.of(
                        makeExistingSubcategory(324L, "parent", makeExistingParent(213L, "otherParent", null)),
                        makeExistingParent(553L, "parent", null)
                ));

        // when
        assertThatThrownBy(() -> categoryCreateUsecase.execute(request))
                .isInstanceOf(CategoryWithTitleAlreadyExistsException.class)
                .hasMessage("Parent category with title 'parent' already exists");

        // then
        verify(categoriesRepository, times(1)).findAllByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(categoriesRepository).findAllByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("parent");

        verify(categoriesRepository, never()).findById(any());
        verify(categoriesRepository, never()).save(any());
    }


    @Test
    void test_execute_createSubcategory_OK() {
        // given
        var request = new CategoryCreateUsecase.Request("subcat123", 100L);

        // and
        when(categoriesRepository.findAllByTitle(any()))
                .thenReturn(List.of(
                        makeExistingSubcategory(324L, "subcat123", makeExistingParent(213L, "otherParent", null)),
                        makeExistingParent(213L, "subcat123", null)
                ));
        when(categoriesRepository.findById(any()))
                .thenReturn(Optional.of(
                        makeExistingParent(100L, "parent", null)
                ));
        when(categoriesRepository.save(any()))
                .thenReturn(
                        makeExistingSubcategory(42L, "subcat123", makeExistingParent(100L, "parent", null))
                );

        // when
        var response = categoryCreateUsecase.execute(request);

        // then
        verify(categoriesRepository, times(1)).findAllByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(categoriesRepository).findAllByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("subcat123");

        verify(categoriesRepository, times(1)).findById(any());
        var identityCaptor = ArgumentCaptor.forClass(Long.class);
        verify(categoriesRepository).findById(identityCaptor.capture());
        assertThat(identityCaptor.getValue()).isEqualTo(100L);

        verify(categoriesRepository, times(1)).save(any());
        var categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoriesRepository).save(categoryCaptor.capture());
        var capturedCategory = categoryCaptor.getValue();
        assertThat(capturedCategory.getIdentity()).isNull();
        assertThat(capturedCategory.getTitle()).isEqualTo("subcat123");
        assertThat(capturedCategory.isParent()).isFalse();
        assertThat(capturedCategory.getParent()).isNotNull();
        assertThat(capturedCategory.getParent().getIdentity()).isEqualTo(100L);
        assertThat(capturedCategory.getParent().getTitle()).isEqualTo("parent");
        assertThat(capturedCategory.getParent().isParent()).isTrue();

        var category = response.getCategory();
        assertThat(category.getIdentity()).isEqualTo(42L);
        assertThat(category.getTitle()).isEqualTo("subcat123");
        assertThat(category.isParent()).isFalse();
        assertThat(category.getParent()).isNotNull();
        assertThat(category.getParent().getIdentity()).isEqualTo(100L);
        assertThat(category.getParent().getTitle()).isEqualTo("parent");
        assertThat(category.getParent().isParent()).isTrue();
    }


    @Test
    void test_execute_createSubcategory_dupTitle_ERROR() {
        // given
        var request = new CategoryCreateUsecase.Request("subcat123", 100L);

        // and
        when(categoriesRepository.findAllByTitle(any()))
                .thenReturn(List.of(
                        makeExistingSubcategory(324L, "subcat123", makeExistingParent(100L, "otherParent", null)),
                        makeExistingParent(213L, "subcat123", null)
                ));

        // when
        assertThatThrownBy(() -> categoryCreateUsecase.execute(request))
                .isInstanceOf(CategoryWithTitleAlreadyExistsException.class)
                .hasMessage("Subcategory with title 'subcat123' and parent id '100' already exists");

        // then
        verify(categoriesRepository, times(1)).findAllByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(categoriesRepository).findAllByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("subcat123");

        verify(categoriesRepository, never()).findById(any());
        verify(categoriesRepository, never()).save(any());
    }


    @Test
    void test_execute_createSubcategory_absentParent_ERROR() {
        // given
        var request = new CategoryCreateUsecase.Request("subcat123", 100L);

        // and
        when(categoriesRepository.findAllByTitle(any()))
                .thenReturn(List.of(
                        makeExistingSubcategory(324L, "subcat123", makeExistingParent(213L, "otherParent", null)),
                        makeExistingParent(213L, "subcat123", null)
                ));
        when(categoriesRepository.findById(any()))
                .thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> categoryCreateUsecase.execute(request))
                .isInstanceOf(CategoryParentNotFoundException.class)
                .hasMessage("Parent category with id '100' is not found");

        // then
        verify(categoriesRepository, times(1)).findAllByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(categoriesRepository).findAllByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("subcat123");

        verify(categoriesRepository, times(1)).findById(any());
        var identityCaptor = ArgumentCaptor.forClass(Long.class);
        verify(categoriesRepository).findById(identityCaptor.capture());
        assertThat(identityCaptor.getValue()).isEqualTo(100L);
    }


    @Test
    void test_execute_createSubcategory_parentIsSubcategory_OK() {
        // given
        var request = new CategoryCreateUsecase.Request("subcat123", 100L);

        // and
        when(categoriesRepository.findAllByTitle(any()))
                .thenReturn(List.of(
                        makeExistingSubcategory(324L, "subcat123", makeExistingParent(213L, "otherParent", null)),
                        makeExistingParent(213L, "subcat123", null)
                ));
        when(categoriesRepository.findById(any()))
                .thenReturn(Optional.of(
                        makeExistingSubcategory(100L, "fakeParent", makeExistingParent(100L, "parent", null))
                ));

        // when
        assertThatThrownBy(() -> categoryCreateUsecase.execute(request))
                .isInstanceOf(CategoryCanNotBeParentException.class)
                .hasMessage("Category with id '100' is subcategory and can not be parent");

        // then
        verify(categoriesRepository, times(1)).findAllByTitle(any());
        var titleCaptor = ArgumentCaptor.forClass(String.class);
        verify(categoriesRepository).findAllByTitle(titleCaptor.capture());
        assertThat(titleCaptor.getValue()).isEqualTo("subcat123");

        verify(categoriesRepository, times(1)).findById(any());
        var identityCaptor = ArgumentCaptor.forClass(Long.class);
        verify(categoriesRepository).findById(identityCaptor.capture());
        assertThat(identityCaptor.getValue()).isEqualTo(100L);
    }

}
