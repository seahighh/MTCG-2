package org.example.application.game.server;

import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;

public interface Application {

    Response handle(Request request);
}
