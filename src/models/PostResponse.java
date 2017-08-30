package models;

public class PostResponse {

    public String responseType;
    public String Message;
    public Todo result;

    public PostResponse() {}

    public PostResponse(String type, String message, Todo result) {
        this.responseType = type;
        this.Message = message;
        this.result = result;
    }
}
