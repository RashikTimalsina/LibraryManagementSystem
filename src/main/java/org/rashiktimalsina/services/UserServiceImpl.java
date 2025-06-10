package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.db.dao.UserDao;
import main.java.org.rashiktimalsina.entities.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

public class UserServiceImpl implements UserService {

//    private final List<User> users = new ArrayList<>();

    private final UserDao userDao;
    private final LoggerService logger;

    public UserServiceImpl(UserDao userDao) {
        this.userDao =  userDao;
        this.logger = LoggerService.getInstance();
    }

    @Override
    public void addUser(User user) {
//        users.add(user);
        try {
            int generatedId = userDao.addUser(user);
            user.setId(generatedId);
//            userDao.addUser(user);
            logger.logUserOperation("ADD_USER", "Added: " + user.getName(), "SUCCESS");
        } catch (SQLException e) {
            logger.logUserOperation("ADD_USER", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }


    @Override
    public User findUserById(int id) {
        //first, traverse through all the users/userlist
//        for (User user : users) {
//            //now check if the user id matches with the required user id from the list and return it
//            if (user.getId().equals(id)) {
//                return user;
//            }
//        }
//        return null;
        try {
            return userDao.findUserById(id);
        } catch (SQLException e) {
            logger.logUserOperation("FIND_USER", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
//        //fetch all users as an arraylist of them
//        return new ArrayList<>(users);
        try {
            return userDao.getAllUsers();
        } catch (SQLException e) {
            logger.logUserOperation("FETCH_USERS", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public boolean updateUser(int id, String name, String email) {
//        // traverse through the users registered in the userlist
//        for (User user : users) {
//            //select the user who is to be updated by checking and matching his existing user id
//            if (user.getId().equals(id)) {
//                //set/update with the new name and email only (id is auto generated)
//                user.setName(name);
//                user.setEmail(email);
//                return true;
//            }
//        }
//        return false
        try {
            boolean updated = userDao.updateUser(id, name, email);
            if (updated) {
                logger.logUserOperation("UPDATE_USER", "Updated: " + id, "SUCCESS");
            } else {
                logger.logUserOperation("UPDATE_USER", "Failed to update: " + id, "ERROR");
            }
            return updated;
        } catch (SQLException e) {
            logger.logUserOperation("UPDATE_USER", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public boolean deleteUser(int id) {

//        //Iterator to remove the selected user by his id
//        Iterator<User> iterator = users.iterator();
//        while (iterator.hasNext()) {
//
//            User user = iterator.next();
//            if (user.getId().equals(id)) {
//                iterator.remove();
//                return true;
//            }
//        }
//
//        return false;
//    }
        try {
            boolean deleted = userDao.deleteUser(id);
            if (deleted) {
                logger.logUserOperation("DELETE_USER", "Deleted: " + id, "SUCCESS");
            } else {
                logger.logUserOperation("DELETE_USER", "Failed to delete: " + id, "ERROR");
            }
            return deleted;
        } catch (SQLException e) {
            logger.logUserOperation("DELETE_USER", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

}

