package org.csjchoisoojong.interaction;

import org.csjchoisoojong.data.Organization;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String answer;
    private final ResponseCode responseCode;
    private ArrayDeque<Organization> organizations;
    public Response(ResponseCode responseCode, String answer, ArrayDeque<Organization> organizations) {
        this.responseCode = responseCode;
        this.answer = answer;
        this.organizations = organizations;
    }
    public String getAnswer() {
        return answer;
    }
    public ResponseCode getResponseCode() {
        return responseCode;
    }
    public ArrayDeque<Organization> getOrganizations() {
        return organizations;
    }
}
