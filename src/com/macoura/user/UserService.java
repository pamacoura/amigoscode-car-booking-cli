package com.macoura.user;

import java.util.Optional;
import java.util.UUID;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User[] getAllUsers(){
        return userDao.getUsers();
    }
    public Optional<User> getUserById(UUID id){
        return userDao.getUserById(id);
    }
}
