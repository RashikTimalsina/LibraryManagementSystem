package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

public class UserServiceImpl implements UserService {

    private final List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public boolean updateUser(String id, String name, String email) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(name);
                user.setEmail(email);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

}