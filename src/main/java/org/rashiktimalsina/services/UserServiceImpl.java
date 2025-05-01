package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.User;
import java.util.ArrayList;
import java.util.Iterator;
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
        //first, traverse through all the users/userlist
        for (User user : users) {
            //now check if the user id matches with the required user id from the list and return it
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        //fetch all users as an arraylist of them
        return new ArrayList<>(users);
    }

    @Override
    public boolean updateUser(String id, String name, String email) {
        // traverse through the users registered in the userlist
        for (User user : users) {
            //select the user who is to be updated by checking and matching his existing user id
            if (user.getId().equals(id)) {
                //set/update with the new name and email only (id is auto generated)
                user.setName(name);
                user.setEmail(email);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(String id) {

        //Iterator to remove the selected user by his id
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {

            User user = iterator.next();
            if (user.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

}

