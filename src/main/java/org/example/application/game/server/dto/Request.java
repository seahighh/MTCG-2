package org.example.application.game.server.dto;

import java.util.HashMap;

public class Request {

    private String method;
    private String path;

    private String contentType;
    private int contentLength;
    private String content;

    private String request;

    HashMap<String, String> headers;
    private String authUser;



    public Request() {
    }

    public Request(String method, String path, String contentType, int contentLength, String content, String request, String authUser, HashMap<String, String> headers) {
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

    public String getAuthUser() {
        return authUser;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    @Override
    public String toString() {
        return request;
    }

//    public void authorizeRequest(){
//        String authorizationHeader = headers.get("Authorization");
//        if (authorizationHeader != null) {
//            String token = authorizationHeader.replace("Basic ", "");
//            String[] parts = token.split("-");
//            if (parts.length == 2) {
//                User user = (User) UserMemoryRepository.getInstance().findByUsername(parts[0]);
//                if (user != null && token.equals(user.getToken())) {
//                    authUser = user;
//                }
//            }
//        }
//
//    }


    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
