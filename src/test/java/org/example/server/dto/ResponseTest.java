package org.example.server.dto;

import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.StatusCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {

    @Test
    void testSetStatusCode() {
        // Arrange
        Response response = new Response();

        // Act
        response.setStatusCode(StatusCode.OK);

        // Assert
        assertEquals(StatusCode.OK.code, response.getStatus());
        assertEquals(StatusCode.OK.message, response.getMessage());
    }
}