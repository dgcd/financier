package dgcd.financier.core.usecase.exception;

public class DatabaseIsNotEmptyException extends IllegalStateException {

    public DatabaseIsNotEmptyException() {
        super("Database must be empty for importing");
    }

}
