package dgcd.financier.core.usecase.exception;

public class AccountHasNonZeroBalanceException extends IllegalArgumentException {

    private static final String MSG = "Account with identity '%d' has non zero balance and can not be closed";

    public AccountHasNonZeroBalanceException(Long identity) {
        super(String.format(MSG, identity));
    }

}
