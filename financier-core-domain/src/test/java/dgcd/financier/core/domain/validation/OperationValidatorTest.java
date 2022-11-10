package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.exception.IllegalIdentityException;
import dgcd.financier.core.domain.exception.IllegalOperationCommentException;
import dgcd.financier.core.domain.exception.IllegalOperationCorrelationException;
import dgcd.financier.core.domain.exception.IllegalOperationCounterpartyException;
import dgcd.financier.core.domain.implementation.GeneralAccount;
import dgcd.financier.core.domain.implementation.GeneralCategory;
import dgcd.financier.core.domain.implementation.GeneralOperation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static dgcd.financier.core.domain.Currency.USD;
import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.OperationType.INCOME;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"UnnecessaryLocalVariable", "ConstantConditions"})
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
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId);
    }


    @Test
    void test_zeroIdentity_ERROR() {
        var identity = 0L;
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(identity, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalIdentityException.class)
                .hasMessage("Identity must be greater then 0 but was: 0");
    }


    @Test
    void test_negativeDate_ERROR() {
        var identity = -42L;
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(identity, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalIdentityException.class)
                .hasMessage("Identity must be greater then 0 but was: -42");
    }


    @Test
    void test_nullDate_ERROR() {
        LocalDate date = null;
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation date can not be null");
    }


    @Test
    void test_nullAccount_ERROR() {
        var date = LocalDate.now();
        Account account = null;
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation account can not be null");
    }


    @Test
    void test_nullType_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        OperationType type = null;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation type can not be null");
    }


    @Test
    void test_nullAmount_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        BigDecimal amount = null;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation amount can not be null");
    }


    @Test
    void test_nullQuantity_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        BigDecimal quantity = null;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation quantity can not be null");
    }


    @Test
    void test_negativeQuantity_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE.negate();
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater the 0");
    }


    @Test
    void test_nullSubcategoryIfNotBase_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        Category subcategory = null;
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Non-BASE operation subcategory can not be null");
    }


    @Test
    void test_nullSubcategoryIfBase_OK() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = BASE;
        var amount = ONE;
        var quantity = ONE;
        Category subcategory = null;
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId);
    }


    @Test
    void test_notNullSubcategoryIfBase_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = BASE;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("BASE operation can not have category");
    }


    @Test
    void test_parentSubcategory_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeParentCategory();
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Subcategory can not be parent");
    }


    @Test
    void test_nullComment_OK() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        String comment = null;
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId);
    }


    @Test
    void test_shortComment_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "1";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalOperationCommentException.class)
                .hasMessage("Operation comment length must be from 2 to 20 but was: 1");
    }


    @Test
    void test_longComment_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "12345678901234567890_";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalOperationCommentException.class)
                .hasMessage("Operation comment length must be from 2 to 20 but was: 21");
    }


    @Test
    void test_nullCounterparty_OK() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        String counterparty = null;
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId);
    }


    @Test
    void test_shortCounterparty_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "1";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalOperationCounterpartyException.class)
                .hasMessage("Operation counterparty length must be from 2 to 20 but was: 1");
    }


    @Test
    void test_longCounterparty_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "12345678901234567890_";
        var isCanceled = FALSE;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalOperationCounterpartyException.class)
                .hasMessage("Operation counterparty length must be from 2 to 20 but was: 21");
    }


    @Test
    void test_nullIsCanceled_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        Boolean isCanceled = null;
        var correlationId = "corr_Id";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Operation isCanceled statis can not be null");
    }


    @Test
    void test_nullCorrelation_OK() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        String correlationId = null;

        new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId);
    }


    @Test
    void test_shortCorrelation_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "1";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalOperationCorrelationException.class)
                .hasMessage("Operation correlation id length must be from 5 to 20 but was: 1");
    }


    @Test
    void test_longCorrelation_ERROR() {
        var date = LocalDate.now();
        var account = makeAccount();
        var type = INCOME;
        var amount = ONE;
        var quantity = ONE;
        var subcategory = makeSubcategory(makeParentCategory());
        var comment = "comment";
        var counterparty = "counterparty";
        var isCanceled = FALSE;
        var correlationId = "12345678901234567890_";

        assertThatThrownBy(() -> new GeneralOperation(null, date, account, type, amount, quantity, subcategory, comment, counterparty, isCanceled, correlationId))
                .isInstanceOf(IllegalOperationCorrelationException.class)
                .hasMessage("Operation correlation id length must be from 5 to 20 but was: 21");
    }

}
