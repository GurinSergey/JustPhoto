package com.gurin.core.dao;

import com.gurin.core.entities.User;

import java.util.List;

/**
 * Created by SGurin on 24.03.2016.
 */
public interface UserDao {
    public void createUser(User user);

    public User updateUser(User user);

    public void removeUser(User user);

    public User findUserById(int id);

    public User findUserByEmail(String name);

    public List<User> findAll();

}
