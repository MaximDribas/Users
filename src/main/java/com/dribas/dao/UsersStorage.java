package com.dribas.dao;

import com.dribas.entity.User;
import java.util.Collection;

public interface UsersStorage {

    Collection<User> getAll();

    User getById(int userId);

    void save(User user);

    void update(User user);

    boolean delete(int userId);
}
