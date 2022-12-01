package org.example.application.game.server.dto;

import org.example.application.game.model.user.User;

import java.util.HashMap;

public class Request {

    private String method;
    private String path;

    private String contentType;
    private int contentLength;
    private String content;

    private String request;

    HashMap<String, String> headers;
    private User authUser;

    public User getAuthUser() {
        return authUser;
    }

    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }

    public Request() {
    }

    public Request(String method, String path, String contentType, int contentLength, String content, String request, User authUser, HashMap<String, String> headers) {
        this.method = method;
        this.path = path;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.content = content;
        this.request = request;
        this.authUser = authUser;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return request;
    }

    public void authorizeRequest(){
        String authorization = headers.get("Authorization");
    }


    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
