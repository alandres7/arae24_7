package co.gov.metropol.area247.util.http;

public class Response {

    private Object body;

    private String message;

    public Object getBody() {
        return body;
    }

    public Response setBody(Object body) {
        this.body = body;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }
}
