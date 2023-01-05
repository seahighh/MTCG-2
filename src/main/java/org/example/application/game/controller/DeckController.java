package org.example.application.game.controller;

import com.google.gson.Gson;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.user.User;
import org.example.application.game.respository.CardRepository;
import org.example.application.game.respository.DeckMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.http.StatusCode;

import java.util.List;

public class DeckController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final DeckMemoryRepository deckMemoryRepository;
    private Gson g;



    public DeckController(CardRepository cardRepository, UserRepository userRepository, DeckMemoryRepository deckMemoryRepository) {

        g = new Gson();
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.deckMemoryRepository = deckMemoryRepository;
    }

    public Response handle(Request request){

        if (request.getMethod().equals(Method.GET.method)) {
            if (request.getAuthUser() == null) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIYED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIYED.message);
                System.out.println(request.getAuthUser());
                return response;
            }
            return readAll(request);

        } else if (request.getMethod().equals(Method.PUT.method)) {

            if (request.getAuthUser() == null) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIYED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIYED.message);
                System.out.println(request.getAuthUser());
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

        User user = userRepository.findByUsername(request.getAuthUser());
        String[] ids = g.fromJson(request.getContent(), String[].class);
        if (ids.length < 4){
            response.setStatusCode(StatusCode.OK);
            response.setContentType(ContentType.APPLICATION_JSON);
            response.setContent("error");
            return response;
        }else {
            deckMemoryRepository.addCardsWithIdsToDeck(ids, user);
            response.setContent("Put cards successful");
            response.setStatusCode(StatusCode.OK);
            response.setContentType(ContentType.APPLICATION_JSON);
            return response;

        }

    }

    private Response readAll(Request request){
        Response response = new Response();
        User user = userRepository.findByUsername(request.getAuthUser());

        List<Card> cards;
        cards = deckMemoryRepository.getDeck(user);
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setContent(g.toJson(cards));
        return response;
    }
}
