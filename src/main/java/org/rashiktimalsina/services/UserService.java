package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.User;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public interface UserService {

    void addUser(User user);

    User findUserById(int id);

    List<User> getAllUsers();

    boolean updateUser(int id, String name, String email);

    boolean deleteUser(int id);

}

