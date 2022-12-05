package org.example.application.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import org.example.application.game.model.user.User;
import org.example.application.game.respository.UserMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.http.StatusCode;

import java.nio.charset.StandardCharsets;

public class UserController {

    private final UserRepository userRepository;
    private Gson g;
    private UserMemoryRepository userMemoryRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) {
            return create(request);
        }

        if (request.getMethod().equals(Method.GET.method)) {
            return readAll();
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;
    }

    private Response readAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        user = User.builder()
                .password(null)
                .token(null)
                .build();
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            content = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }


    private Response create(Request request) {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = request.getContent();
        User user;
        try {
            user = objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String content_a;
        try {
            content_a = objectMapper.writeValueAsString(userRepository.findByUsername(user.getUsername()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (content_a.equals("null")){
            user = userRepository.save(
                    user.toBuilder()
                            .token(user.getUsername() + "-" + "mtcgToken")
                            .password(Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString())
                            .build());
            Response response = new Response();
            response.setStatusCode(StatusCode.CREATED);
            response.setContentType(ContentType.APPLICATION_JSON);
            String content;
            try {
                content = objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            response.setContent(content);

            return response;
        }
        else {
            Response response = new Response();
            response.setStatusCode(StatusCode.CREATED);
            response.setContentType(ContentType.APPLICATION_JSON);
            String content_b = "User already exist!";
            response.setContent(content_b);
            return response;
        }

    }

}
