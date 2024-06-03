package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.model.Account;

import static dgcd.financier.core.domain.Constants.ACCOUNT_TITLE_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.ACCOUNT_TITLE_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.AMOUNT_SCALE;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkId;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkLength;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkMaxScale;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkNonNull;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkZero;

public class AccountValidator {

    public static Account validate(Account account) {
        checkId(account.getId(), "id");

        checkNonNull(account.getTitle(), "title");
        checkLength(account.getTitle(), ACCOUNT_TITLE_MIN_LENGTH, ACCOUNT_TITLE_MAX_LENGTH, "title");

        checkNonNull(account.getCurrency(), "currency");

        checkNonNull(account.getBalance(), "balance");
        checkMaxScale(account.getBalance(), AMOUNT_SCALE, "balance");
        if (account.isClosed()) {
            checkZero(account.getBalance(), "balance");
        }

        return account;
    }

}
