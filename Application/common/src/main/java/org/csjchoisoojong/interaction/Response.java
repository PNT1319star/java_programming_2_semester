package org.csjchoisoojong.interaction;

import java.io.Serial;
import java.io.Serializable;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String answer;
    private final String[] arguments;
    private final ResponseCode responseCode;
    public Response(ResponseCode responseCode, String answer, String[] arguments ) {
        this.responseCode = responseCode;
        this.answer = answer;
        this.arguments = arguments;
    }
    public String getAnswer() {
        return answer;
    }
    public String[] getArguments() {
        return arguments;
    }
    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
