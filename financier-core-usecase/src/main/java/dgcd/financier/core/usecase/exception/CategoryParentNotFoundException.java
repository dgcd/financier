package dgcd.financier.core.usecase.exception;

public class CategoryParentNotFoundException extends IllegalArgumentException {

    private static final String MSG = "Parent category with id '%d' is not found";

    public CategoryParentNotFoundException(Long id) {
        super(String.format(MSG, id));
    }

}
