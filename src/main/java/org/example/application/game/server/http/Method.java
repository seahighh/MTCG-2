package org.example.application.game.server.http;

public enum Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    public final String method;
    Method(String method) {
        this.method = method;
    }
}
