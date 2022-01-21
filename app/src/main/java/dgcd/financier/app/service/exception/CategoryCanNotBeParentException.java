package dgcd.financier.app.service.exception;

public class CategoryCanNotBeParentException extends ServiceException {

    private static final String MSG = "Category with id '%d' can not be parent";

    public CategoryCanNotBeParentException(Long id) {
        super(String.format(MSG, id));
    }

}
