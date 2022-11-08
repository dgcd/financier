package dgcd.financier.core.usecase.exception;

public class CategoryCanNotBeParentException extends IllegalArgumentException {

    private static final String MSG = "Category with id '%d' is subcategory and can not be parent";

    public CategoryCanNotBeParentException(Long id) {
        super(String.format(MSG, id));
    }

}
