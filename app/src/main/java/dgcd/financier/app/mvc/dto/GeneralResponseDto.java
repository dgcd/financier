package dgcd.financier.app.mvc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GeneralResponseDto(
        ResponseCode code,
        String message,     // if error
        Object payload      // if OK
) {

    // if OK
    public GeneralResponseDto(Object payload) {
        this(ResponseCode.OK, null, payload);
    }

    // if error
    public GeneralResponseDto(ResponseCode code, String message) {
        this(code, message, null);
    }

}
