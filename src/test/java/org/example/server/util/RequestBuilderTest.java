package org.example.server.util;

import org.example.application.game.server.dto.Request;
import org.example.application.game.server.exception.UnsupportedProtocolException;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.util.RequestBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestBuilderTest {

    @Test
    void testBuild() throws UnsupportedProtocolException {
        // Arrange
        String httpRequest =
                """
                POST /hello HTTP/1.1
                Authorization: Basic hello-world-token
                Content-Type: application/json
                User-Agent: PostmanRuntime/7.28.3
                Accept: */*
                Host: localhost:10001
                Accept-Encoding: gzip, deflate, br
                Connection: keep-alive
                Content-Length: 29
                                
                {
                    "message": "World!"
                }
                """;

        // Act
        Request request = RequestBuilder.build(httpRequest);

        // Assert
        assertEquals(29, request.getContentLength());
        assertEquals(Method.POST.method, request.getMethod());
    }

    @Test
    void testBuildException() {
        String randomString =
                """
                Lorem ipsum ...
                """;

        assertThrows(
                UnsupportedProtocolException.class,
                () -> RequestBuilder.build(randomString)
        );
    }

}