package dgcd.financier.app.modules.category.exception;

import dgcd.financier.app.commons.service.exception.ServiceException;

public class ParentCategoryNotFoundException extends ServiceException {

    private static final String MSG = "Parent category with id '%d' is not found";

    public ParentCategoryNotFoundException(Long id) {
        super(String.format(MSG, id));
    }

}
