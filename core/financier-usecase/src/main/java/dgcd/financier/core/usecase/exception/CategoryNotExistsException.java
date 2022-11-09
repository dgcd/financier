package dgcd.financier.core.usecase.exception;

public class CategoryNotExistsException extends IllegalArgumentException {

    private static final String MSG = "Category with identity '%d' doesn't exist";

    public CategoryNotExistsException(Long identity) {
        super(String.format(MSG, identity));
    }

}
