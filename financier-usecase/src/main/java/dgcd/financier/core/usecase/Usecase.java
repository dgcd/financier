package dgcd.financier.core.usecase;

public interface Usecase<REQ extends Usecase.Request, RESP extends Usecase.Response> {

    RESP execute(REQ request);

    interface Request {
    }

    interface Response {
    }

}
