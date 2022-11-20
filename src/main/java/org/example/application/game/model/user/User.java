package org.example.application.game.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
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

    private int coins;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

//    public boolean authorize(String password) {
//        String passwordHash = Hashing.sha256()
//                //bcrypt
//                .hashString(password, StandardCharsets.UTF_8)
//                .toString();
//        return passwordHash.equals(getPassword());
//    }


}
