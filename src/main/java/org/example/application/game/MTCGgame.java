package org.example.application.game;

import org.example.application.game.controller.CardController;
import org.example.application.game.controller.SessionController;
import org.example.application.game.controller.UserController;
import org.example.application.game.respository.CardMemoryRepository;
import org.example.application.game.respository.CardRepository;
import org.example.application.game.respository.UserMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.application.game.server.Application;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.StatusCode;

public class MTCGgame implements Application {

    private UserController userController;
    private CardController cardController;
    private SessionController sessionController;

    public MTCGgame() {
        UserRepository userRepository = new UserMemoryRepository();
        this.userController = new UserController(userRepository);
        CardRepository cardRepository = new CardMemoryRepository();
        this.cardController = new CardController(cardRepository);
        this.sessionController = new SessionController(userRepository);

    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {//sessionsController
            return userController.handle(request);
        }
        if (request.getPath().startsWith("/sessions")){
            return sessionController.handle(request);
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
