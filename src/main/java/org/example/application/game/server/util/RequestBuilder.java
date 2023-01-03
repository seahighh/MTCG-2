package org.example.application.game.server.util;

import org.example.application.game.server.dto.Request;
import org.example.application.game.server.exception.UnsupportedProtocolException;

public class RequestBuilder {

    /**
     * Takes an HTTP request as a string and builds and
     * returns a Request object.
     *
     * @param requestString HTTP request as a string
     * @return HTTP request as an object
     */
    public static Request build(String requestString) throws UnsupportedProtocolException {
        Request request = new Request();
        request.setRequest(requestString);

        // Method
        request.setMethod(getMethod(requestString));

        // Path
        request.setPath(getPath(requestString));

        // Content Length
        request.setContentLength(HttpRegex.findHeaderAsInt(requestString, "Content-Length"));

        //Content Authorization of Header
        request.setAuthUser(HttpRegex.findHeaderAsAuth(requestString, "Authorization"));

        // Content
        request.setContent(HttpRegex.findContent(requestString));

        // TODO: Add additional information to the request

        return request;
    }

    private static String getMethod(String requestString) throws UnsupportedProtocolException {
        String method = HttpRegex.findMethod(requestString);

        if (null == method) {
            throw new UnsupportedProtocolException("No HTTP method in request");
        }

        return method;
    }

    private static String getPath(String requestString) throws UnsupportedProtocolException {
        String path = HttpRegex.findPath(requestString);
        if (null == path) {
            throw new UnsupportedProtocolException("No HTTP path in request");
        }

        return path;
    }
}