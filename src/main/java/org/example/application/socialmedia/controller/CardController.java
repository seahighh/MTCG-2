package org.example.application.socialmedia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.card.CardType;
import org.example.application.socialmedia.model.Card;
import org.example.application.socialmedia.respository.PackagesRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class CardController {

    private final PackagesRepository packagesRepository;

    public CardController(PackagesRepository packagesRepository){
        this.packagesRepository = packagesRepository;
    }

    public Response handle(Request request){
        if (request.getMethod().equals(Method.POST.method)){
            return create(request);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;
    }

    private Response readAll(){
        ObjectMapper objectMapper = new ObjectMapper();
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            content = objectMapper.writeValueAsString(packagesRepository.findAll());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);
        return response;

    }

    private Response create(Request request){
        ObjectMapper objectMapper = new ObjectMapper();

        String json = request.getContent();
        Card card;
        try {
            card = objectMapper.readValue(json, Card.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        card = packagesRepository.save(card);

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            content = objectMapper.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);
        return response;

    }
}
