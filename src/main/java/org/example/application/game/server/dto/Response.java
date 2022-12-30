package org.example.application.game.server.dto;

import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.StatusCode;

public class Response {

    private int status;
    private String message;

    private String contentType;
    private String content = "";

    private String Authorization;

    public void setStatusCode(StatusCode statusCode) {
        this.status = statusCode.code;
        this.message = statusCode.message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType.contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return
                "HTTP/1.1 " + status + " " + message + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + content.length() + "\r\n" +
                "\r\n" +
                content;
    }
}
