package org.example.application.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.user.User;
import org.example.application.game.respository.CardRepository;
import org.example.application.game.respository.UserMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.http.StatusCode;

import java.util.ArrayList;
import java.util.List;

public class CardController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private Gson g;

    public CardController(CardRepository cardRepository, UserRepository userRepository){
        g = new Gson();
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Response handle(Request request){
        if (request.getMethod().equals(Method.GET.method)){
            if (request.getAuthUser() == null) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIYED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIYED.message);
                System.out.println(request.getAuthUser());
                return response;
            }
            return readAll(request);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;
    }

    private Response readAll(Request request){
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);

        User user = userRepository.findByUsername(request.getAuthUser());

        List<Card> cards = new ArrayList<>();
        cards = cardRepository.getCardsForUser(user);

        response.setContent(g.toJson(cards));
        return response;

    }
}
