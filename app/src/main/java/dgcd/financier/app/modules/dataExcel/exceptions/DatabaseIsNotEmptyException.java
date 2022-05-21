package dgcd.financier.app.modules.dataExcel.exceptions;

public class DatabaseIsNotEmptyException extends ImportingException {

    public DatabaseIsNotEmptyException() {
        super("Database must be empty for importing");
    }

}
