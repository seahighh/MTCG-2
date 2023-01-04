package org.example.application.game;

import org.example.application.game.controller.CardController;
import org.example.application.game.controller.PackageController;
import org.example.application.game.controller.SessionController;
import org.example.application.game.controller.UserController;
import org.example.application.game.respository.*;
import org.example.application.game.server.Application;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.StatusCode;

public class MTCGgame implements Application {

    private UserController userController;
    private CardController cardController;
    private SessionController sessionController;
    private PackageController packageController;

    public MTCGgame() {
        UserRepository userRepository = new UserMemoryRepository();
        CardRepository cardRepository = new CardMemoryRepository();
        PackageRepository packageRepository = new PackageMemoryRepository();
        this.userController = new UserController(userRepository);
        this.cardController = new CardController(cardRepository, userRepository);
        this.sessionController = new SessionController(userRepository);
        this.packageController = new PackageController(packageRepository, cardRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
            return userController.handle(request);
        }
        if (request.getPath().startsWith("/sessions")){
            return sessionController.handle(request);
        }

        if (request.getPath().startsWith("/packages")){
            return packageController.handle(request);
        }

        if (request.getPath().startsWith("/transactions")){
            return packageController.handle(request);
        }

        //其实下面的代码是不会执行的，直接return对应的handle里去处理了
        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}
