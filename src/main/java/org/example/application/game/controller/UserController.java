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
import java.util.HashMap;
import java.util.Map;

public class UserController {

    private final UserRepository userRepository;
    private Gson g;
    private UserMemoryRepository userMemoryRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response handle(Request request) {
        //我会有点不理解你叫我做什么，所以我先判断一下方法，是post还是get使用不同的处理方法
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
        //jackon and json, 序列化和反序列化, json data to our object content
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

        //从检测文件里获取参数封装到User里，这样就能创建了
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

//        if (userRepository.save(user)){
//            ...
//            ...;
//        }else{
//            ...
//            ...
//        }
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
            Map map = new HashMap();
            map.put("Message: ", "User created successfull");
            map.put("User_Info: ", user.getUsername());
            try {
                content = objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            response.setContent(content);
            return response;
        }
        else {
            Response response = new Response();
            response.setStatusCode(StatusCode.OK);
            response.setContentType(ContentType.APPLICATION_JSON);
            String content_b = "User already exist!";
            response.setContent(content_b);
            return response;
        }

    }

}
