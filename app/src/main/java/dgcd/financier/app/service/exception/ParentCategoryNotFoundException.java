package dgcd.financier.app.service.exception;

public class ParentCategoryNotFoundException extends ServiceException {

    private static final String MSG = "Parent category with id '%d' is not found";

    public ParentCategoryNotFoundException(Long id) {
        super(String.format(MSG, id));
    }

}
