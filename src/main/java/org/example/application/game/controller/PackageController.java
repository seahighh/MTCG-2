package org.example.application.game.controller;

import com.google.gson.Gson;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.card.Package;
import org.example.application.game.model.user.User;
import org.example.application.game.respository.CardRepository;
import org.example.application.game.respository.PackageRepository;
import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.http.ContentType;
import org.example.application.game.server.http.Method;
import org.example.application.game.server.http.StatusCode;

public class PackageController {
    private final PackageRepository packageRepository;
    private final CardRepository cardRepository;
    Gson gson;

    public PackageController(PackageRepository packageRepository, CardRepository cardRepository) {
        gson = new Gson();
        this.packageRepository = packageRepository;
        this.cardRepository = cardRepository;
    }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) {
            //admin can creat package, every package have 5 cards.
            if (request.getAuthUser() == null || !"admin".equalsIgnoreCase(request.getAuthUser())) {
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

    public Response handleAcquirePackages(Request request){

        User user = new User();
        user.setUsername(request.getAuthUser());
        Package cardpackage = packageRepository.getRandomPackage();
        if (cardpackage != null && packageRepository.addPackageToUser(cardpackage, user)){
            Response response = new Response();
            response.setStatusCode(StatusCode.CREATED);
            response.setContentType(ContentType.APPLICATION_JSON);
            response.setContent("cards acquired successful");
            return response;
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.BADREQUEST.message);
        return response;


    }

    private Response create(Request request) {
        Gson gson = new Gson();
        Package cardPackage = packageRepository.addPackage();

        Card[] cards = gson.fromJson(request.getContent(),Card[].class);

        //loop for save 5 cards get from bat test files
        for(Card card:cards){
            card = cardRepository.save(card);
            cardRepository.addCardToPackage(card,cardPackage.getId());
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setContent("package create successful");
        return response;
    }


}
