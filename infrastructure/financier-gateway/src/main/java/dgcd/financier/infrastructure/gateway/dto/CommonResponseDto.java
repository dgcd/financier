package dgcd.financier.infrastructure.gateway.dto;

public record CommonResponseDto(
        Object payload,         // if OK
        String errorMessage     // if error
) {

    public static CommonResponseDto ok(Object payload) {
        return new CommonResponseDto(payload, null);
    }

    public static CommonResponseDto error(String errorMessage) {
        return new CommonResponseDto(null, errorMessage);
    }

}
