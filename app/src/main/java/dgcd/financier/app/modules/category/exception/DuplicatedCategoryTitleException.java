package dgcd.financier.app.modules.category.exception;

import dgcd.financier.app.commons.service.exception.ServiceException;

public class DuplicatedCategoryTitleException extends ServiceException {

    private static final String MSG = "Duplicated category title '%s' with parent id '%d'";

    public DuplicatedCategoryTitleException(String title, Long parentId) {
        super(String.format(MSG, title, parentId));
    }

}
