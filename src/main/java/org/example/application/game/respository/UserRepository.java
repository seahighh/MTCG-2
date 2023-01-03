package org.example.application.game.respository;

import org.example.application.game.model.user.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();
    User getUserById(int id);

    User findByUsername(String username);

    User save(User user);
    User updateCoin(User user);
    User login(String username, String password);
//    User updateUser(String name, User user);

    User delete(User user);
}
