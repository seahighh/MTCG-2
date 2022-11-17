package org.example.application.game.respository;

import org.example.application.game.model.user.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByUsername(String username);

    User save(User user);

    User delete(User user);
}
