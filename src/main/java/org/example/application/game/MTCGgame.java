package org.example.application.game;

import org.example.application.game.controller.*;
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
    private DeckController deckController;
    private StatsController statsController;
    private ScoreboardController scoreboardController;
    private BattleController battleController;

    public MTCGgame() {
        UserRepository userRepository = new UserMemoryRepository();
        CardRepository cardRepository = new CardMemoryRepository();
        PackageRepository packageRepository = new PackageMemoryRepository();
        DeckMemoryRepository deckMemoryRepository = new DeckMemoryRepository();
        StatsMemoryRepository statsMemoryRepository = new StatsMemoryRepository();
        BattleMemoryRepository battleMemoryRepository = new BattleMemoryRepository();
        this.userController = new UserController(userRepository);
        this.cardController = new CardController(cardRepository, userRepository);
        this.sessionController = new SessionController(userRepository);
        this.packageController = new PackageController(packageRepository, cardRepository);
        this.deckController = new DeckController(cardRepository, userRepository, deckMemoryRepository);
        this.statsController = new StatsController(userRepository, statsMemoryRepository);
        this.scoreboardController = new ScoreboardController(userRepository, statsMemoryRepository);
        this.battleController = new BattleController(cardRepository, userRepository, deckMemoryRepository, battleMemoryRepository);
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

        if (request.getPath().startsWith("/transactions/packages")){
            return packageController.handleAcquirePackages(request);
        }
        if (request.getPath().startsWith("/cards")){
            return cardController.handle(request);
        }
        if (request.getPath().startsWith("/deck")){
            return deckController.handle(request);
        }
        if (request.getPath().startsWith("/stats")){
            return statsController.handle(request);
        }
        if (request.getPath().startsWith("/score")){
            return scoreboardController.handle(request);
        }
        if (request.getPath().startsWith("/battles")){
            return battleController.handle(request);
        }

        //其实下面的代码是不会执行的，直接return对应的handle里去处理了
        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}
