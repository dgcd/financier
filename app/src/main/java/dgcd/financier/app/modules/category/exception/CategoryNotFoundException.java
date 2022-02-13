package dgcd.financier.app.modules.category.exception;

import dgcd.financier.app.commons.service.exception.ServiceException;

public class CategoryNotFoundException extends ServiceException {

    private static final String MSG = "Category with id '%d' is not founds";

    public CategoryNotFoundException(Long id) {
        super(String.format(MSG, id));
    }

}
