package org.example.application.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.application.game.model.user.User;
import org.example.application.game.respository.UserMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.http.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class SessionController {
    private final UserRepository userRepository;
    private Gson g;
    private UserMemoryRepository userMemoryRepository;

    public SessionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) {
            return read(request);
        }


        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;
    }


    private Response read(Request request){
        Response response = new Response();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();
        User user;

        try {
            user = objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        try {
            String content;
            content = objectMapper.writeValueAsString(userRepository.login(user.getUsername(), user.getPassword()));
            if (content.equals("null")){
                content = "Login fail";
                response.setContent(content);
            }else {
                User user2 = userRepository.login(user.getUsername(), user.getPassword());
                String Authorization_Name = user2.getUsername();
                Map map = new HashMap();
                map.put("Message: ", "Login successful");
                map.put("Authorization_User: ", Authorization_Name);
                content = objectMapper.writeValueAsString(map);
                response.setContent(content);
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
