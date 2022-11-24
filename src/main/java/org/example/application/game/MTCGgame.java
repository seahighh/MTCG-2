package org.example.application.game;

import org.example.application.game.controller.CardController;
import org.example.application.game.controller.UserController;
import org.example.application.game.respository.CardMemoryRepository;
import org.example.application.game.respository.CardRepository;
import org.example.application.game.respository.UserMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class MTCGgame implements Application {

    private UserController userController;
    private CardController cardController;

    public MTCGgame() {
        UserRepository userRepository = new UserMemoryRepository();
        this.userController = new UserController(userRepository);
        CardRepository cardRepository = new CardMemoryRepository();
        this.cardController = new CardController(cardRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")||request.getPath().startsWith("/sessions")) {//sessionsController
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/packages")){
            return cardController.handle(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}
