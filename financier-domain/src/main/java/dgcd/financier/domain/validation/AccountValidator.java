package dgcd.financier.domain.validation;

import dgcd.financier.domain.Account;
import dgcd.financier.domain.exception.IllegalAccountTitleException;

import static dgcd.financier.domain.Constants.ACCOUNT_TITLE_MAX_LENGTH;
import static dgcd.financier.domain.Constants.ACCOUNT_TITLE_MIN_LENGTH;
import static dgcd.financier.domain.validation.ValidationUtils.checkStringBoundaries;
import static java.util.Objects.requireNonNull;

public class AccountValidator {

    public static void validate(Account account) {
        var title = account.getTitle();
        requireNonNull(title, "Title can not be null");
        checkStringBoundaries(
                title,
                ACCOUNT_TITLE_MIN_LENGTH,
                ACCOUNT_TITLE_MAX_LENGTH,
                IllegalAccountTitleException::new
        );

        requireNonNull(account.getCurrency(), "Currency can not be null");
        requireNonNull(account.getBalance(), "Balance can not be null");
        requireNonNull(account.getIsClosed(), "IsClosed status can not be null");
    }

}
