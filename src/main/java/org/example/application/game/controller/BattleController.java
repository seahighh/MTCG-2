package org.example.application.game.controller;

import com.google.gson.Gson;
import org.example.application.game.model.battle.Battle;
import org.example.application.game.model.user.User;
import org.example.application.game.respository.BattleMemoryRepository;
import org.example.application.game.respository.CardRepository;
import org.example.application.game.respository.DeckMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.http.StatusCode;

import java.util.ArrayList;
import java.util.List;

public class BattleController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final DeckMemoryRepository deckMemoryRepository;
    private final BattleMemoryRepository battleMemoryRepository;
    List<User> users = new ArrayList<>();
    private Gson g;

    public BattleController(CardRepository cardRepository, UserRepository userRepository, DeckMemoryRepository deckMemoryRepository, BattleMemoryRepository battleMemoryRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.deckMemoryRepository = deckMemoryRepository;
        this.battleMemoryRepository = battleMemoryRepository;
        g = new Gson();

    }

    public Response handle(Request request){

        if (request.getMethod().equals(Method.POST.method)) {
            if (request.getAuthUser() == null) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIYED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIYED.message);

                return response;
            }
            return create(request);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;

    }
    private Response create(Request request){
        Response response = new Response();

        Battle battle = new Battle();
        User user = userRepository.findByUsername(request.getAuthUser());
        users.add(user);
        if (users.size() == 2){
            battle = battleMemoryRepository.addBattle(battle);

            for (User user1 : users){
                battle = battleMemoryRepository.addUserToBattle(user1, battle);

            }

            response.setStatusCode(StatusCode.OK);
            response.setContentType(ContentType.APPLICATION_JSON);
            response.setContent("Battle finished");
            return response;


        }else {
            response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
            response.setContentType(ContentType.TEXT_PLAIN);
            response.setContent("Now only havs one player");
            return response;
        }

    }
}
