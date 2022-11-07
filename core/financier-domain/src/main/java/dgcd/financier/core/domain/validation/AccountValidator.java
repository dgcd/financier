package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Constants;
import dgcd.financier.core.domain.exception.IllegalAccountTitleException;

import static dgcd.financier.core.domain.validation.ValidationUtils.checkIdentity;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkStringBoundaries;
import static java.util.Objects.requireNonNull;

public class AccountValidator {

    public static void validate(Account account) {
        checkIdentity(account.getIdentity());

        var title = account.getTitle();
        requireNonNull(title, "Title can not be null");
        checkStringBoundaries(
                title,
                Constants.ACCOUNT_TITLE_MIN_LENGTH,
                Constants.ACCOUNT_TITLE_MAX_LENGTH,
                IllegalAccountTitleException::new
        );

        requireNonNull(account.getCurrency(), "Currency can not be null");
        requireNonNull(account.getBalance(), "Balance can not be null");
        requireNonNull(account.getIsClosed(), "IsClosed status can not be null");
    }

}
