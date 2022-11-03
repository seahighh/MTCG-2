package org.example.application.waiting;

import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class WaitingApp implements Application {

    @Override
    public Response handle(Request request) {
        System.out.println(Thread.currentThread());

        if (request.getPath().equals("/favicon.ico")) {
            Response response = new Response();
            response.setStatusCode(StatusCode.NOT_FOUND);
            response.setContentType(ContentType.TEXT_PLAIN);
            response.setContent(StatusCode.NOT_FOUND.message);
            return response;
        }

        if (request.getPath().equals("/timeout")) {
            while (true);
        }


        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.OK.message);
        return response;
    }
}
