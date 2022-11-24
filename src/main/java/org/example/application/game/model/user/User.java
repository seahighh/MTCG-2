package org.example.application.game.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;


@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Builder
public class User {

    private String username;

    private String password;

    private String token;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private int coins = 20;



    public User() {
    }

    public User(String username, String password, String token, String status, int coins) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.status = status;
        this.coins = 20;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

//    public boolean authorize(String password) {
//        String passwordHash
//    }


}
