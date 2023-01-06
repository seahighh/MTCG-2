package org.example.application.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.application.game.model.stats.Stats;
import org.example.application.game.model.user.User;
import org.example.application.game.respository.StatsMemoryRepository;
import org.example.application.game.respository.UserRepository;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.http.StatusCode;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatsController {

    private final UserRepository userRepository;
    private final StatsMemoryRepository statsMemoryRepository;

    private Gson g;

    public StatsController(UserRepository userRepository, StatsMemoryRepository statsMemoryRepository) {
        this.userRepository = userRepository;
        this.statsMemoryRepository = statsMemoryRepository;
    }

    public Response handle(Request request){
        if (request.getMethod().equals(Method.GET.method)) {
            if (request.getAuthUser() == null) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIYED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIYED.message);

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

    public Response readAll(Request request){
        ObjectMapper objectMapper = new ObjectMapper();
        Response response = new Response();
        User user = userRepository.findByUsername(request.getAuthUser());

        Stats stats = statsMemoryRepository.getStatsForUser(user);

        String content;
        Map map = new LinkedHashMap();

        map.put("Player: ", user.getUsername());
        map.put("Total: ", stats.getBattles());
        map.put("Win: ", stats.getNumbersOfWin());
        map.put("Lost: ", stats.getNumbersOfLost());
        map.put("Elo: ", stats.getElo());

        try {
            content = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setContent(content);
        return response;
    }
}
