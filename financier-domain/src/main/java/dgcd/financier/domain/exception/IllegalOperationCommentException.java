package dgcd.financier.domain.exception;

import static dgcd.financier.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;

public class IllegalOperationCommentException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Operation comment length must be from %d to %d but was: ",
            OPERATION_COMMENT_MIN_LENGTH,
            OPERATION_COMMENT_MAX_LENGTH
    );

    public IllegalOperationCommentException(String comment) {
        super(MSG.concat(String.valueOf(comment.length())));
    }

}
