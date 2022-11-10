package dgcd.financier.core.domain.exception;

import dgcd.financier.core.domain.Constants;

public class IllegalOperationCommentException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Operation comment length must be from %d to %d but was: ",
            Constants.OPERATION_COMMENT_MIN_LENGTH,
            Constants.OPERATION_COMMENT_MAX_LENGTH
    );

    public IllegalOperationCommentException(String comment) {
        super(MSG.concat(String.valueOf(comment.length())));
    }

}
