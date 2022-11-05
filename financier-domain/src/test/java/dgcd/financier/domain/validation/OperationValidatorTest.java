package dgcd.financier.domain.validation;

import dgcd.financier.domain.Account;
import dgcd.financier.domain.Category;
import dgcd.financier.domain.exception.IllegalOperationCommentException;
import dgcd.financier.domain.exception.IllegalOperationCorrelationException;
import dgcd.financier.domain.exception.IllegalOperationCounterpartyException;
import dgcd.financier.domain.implementation.GeneralAccount;
import dgcd.financier.domain.implementation.GeneralCategory;
import dgcd.financier.domain.implementation.GeneralOperation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dgcd.financier.domain.Currency.USD;
import static dgcd.financier.domain.OperationType.INCOME;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

class OperationValidatorTest {

    private Account makeAccount() {
        return new GeneralAccount(null, "account", USD, ZERO, FALSE);
    }

    private Category makeParentCategory() {
        return new GeneralCategory(null, "parent", null);
    }

    private Category makeSubcategory(Category parent) {
        return new GeneralCategory(null, "child", parent);
    }


    @Test
    void test_OK() {
        new GeneralOperation(
                null,
                LocalDate.now(),
                makeAccount(),
                INCOME,
                ONE,
                ONE,
                makeSubcategory(makeParentCategory()),
                "comment",
                "counterparty",
                FALSE,
                null
        );
    }


    @Test
    void test_nullDate_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        null,
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation date can not be null");
    }


    @Test
    void test_nullAccount_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        null,
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation account can not be null");
    }


    @Test
    void test_nullType_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        null,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation type can not be null");
    }


    @Test
    void test_nullAmount_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        null,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation amount can not be null");
    }


    @Test
    void test_nullQuantity_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        null,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation quantity can not be null");
    }


    @Test
    void test_negativeQuantity_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE.negate(),
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater the 0");
    }


    @Test
    void test_nullSubcategory_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        null,
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation subcategory can not be null");
    }


    @Test
    void test_parentSubcategory_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeParentCategory(),
                        "comment",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Subcategory can not be parent");
    }


    @Test
    void test_shortComment_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "1",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(IllegalOperationCommentException.class)
                .hasMessage("Operation comment length must be from 2 to 20 but was: 1");
    }


    @Test
    void test_longComment_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "12345678901234567890_",
                        "counterparty",
                        FALSE,
                        null
                ))
                .isInstanceOf(IllegalOperationCommentException.class)
                .hasMessage("Operation comment length must be from 2 to 20 but was: 21");
    }


    @Test
    void test_shortCounterparty_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "1",
                        FALSE,
                        null
                ))
                .isInstanceOf(IllegalOperationCounterpartyException.class)
                .hasMessage("Operation counterparty length must be from 2 to 20 but was: 1");
    }


    @Test
    void test_longCounterparty_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "12345678901234567890_",
                        FALSE,
                        null
                ))
                .isInstanceOf(IllegalOperationCounterpartyException.class)
                .hasMessage("Operation counterparty length must be from 2 to 20 but was: 21");
    }


    @Test
    void test_nullIsCanceled_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        null,
                        null
                ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation isCanceled statis can not be null");
    }


    @Test
    void test_shortCorrelation_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        "1"
                ))
                .isInstanceOf(IllegalOperationCorrelationException.class)
                .hasMessage("Operation correlation id length must be from 5 to 20 but was: 1");
    }


    @Test
    void test_longCorrelation_ERROR() {
        Assertions
                .assertThatThrownBy(() -> new GeneralOperation(
                        null,
                        LocalDate.now(),
                        makeAccount(),
                        INCOME,
                        ONE,
                        ONE,
                        makeSubcategory(makeParentCategory()),
                        "comment",
                        "counterparty",
                        FALSE,
                        "12345678901234567890_"
                ))
                .isInstanceOf(IllegalOperationCorrelationException.class)
                .hasMessage("Operation correlation id length must be from 5 to 20 but was: 21");
    }

}
