package dgcd.financier.core.usecase.exception;

public class CategoryWithTitleAlreadyExistsException extends IllegalArgumentException {

    private static final String MSG_PARENT = "Parent category with title '%s' already exists";
    private static final String MSG_SUB = "Subcategory with title '%s' and parent id '%d' already exists";

    public CategoryWithTitleAlreadyExistsException(String title) {
        super(String.format(MSG_PARENT, title));
    }

    public CategoryWithTitleAlreadyExistsException(String title, Long parentIdentity) {
        super(String.format(MSG_SUB, title, parentIdentity));
    }

}
