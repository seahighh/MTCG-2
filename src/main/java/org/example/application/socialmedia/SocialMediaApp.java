package org.example.application.socialmedia;

import org.example.application.socialmedia.controller.CardController;
import org.example.application.socialmedia.respository.PackagesMemoryRepository;
import org.example.application.socialmedia.respository.PackagesRepository;
import org.example.application.socialmedia.controller.UserController;
import org.example.application.socialmedia.respository.UserMemoryRepository;
import org.example.application.socialmedia.respository.UserRepository;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class SocialMediaApp implements Application {

    private UserController userController;
    private CardController cardController;

    public SocialMediaApp() {
        UserRepository userRepository = new UserMemoryRepository();
        this.userController = new UserController(userRepository);
        PackagesRepository packagesRepository = new PackagesMemoryRepository();
        this.cardController = new CardController(packagesRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
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
