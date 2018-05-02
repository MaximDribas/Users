package com.dribas.dao;

import com.dribas.entity.User;

import java.util.List;

public interface UsersStorage {

    List<User> getAll();

    User getById(int userId);

    void save(User user);

    void update(User user);

    boolean delete(int userId);
}
