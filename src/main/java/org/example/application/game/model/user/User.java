package org.example.application.game.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;


@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Builder(toBuilder = true)
public class User {
    private int id;

    private String username;

    private String password;

    private String token;

    private String status;
    private int coins = 20;
    private String Name;
    private String Bio;
    private String Image;
    private int elo = 100;
    private int rank;

    public User(int id, String username, String password, String token, String status, int coins, String Name, String bio, String image, int elo, int rank) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token;
        this.status = status;
        this.coins = coins;
        this.Name = Name;
        this.Bio = bio;
        this.Image = image;
        this.elo = elo;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getRank() {
        return rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public User() {
    }

    public void setRank(int rank) {
        this.rank = rank;
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


}
