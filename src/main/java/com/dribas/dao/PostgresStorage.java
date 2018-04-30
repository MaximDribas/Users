package com.dribas.dao;

import com.dribas.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Transactional
@Repository
public class PostgresStorage implements UsersStorage {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> getAll() {
        String hql = "SELECT FROM User as us ORDER BY us.lastName";
        return (Collection<User>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public User getById(int userId) {
        String hql = "FROM Users WHERE userId =:userId";
        User user = (User) entityManager.createQuery(hql).setParameter("userId", userId).getSingleResult();
        return user;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        User updatedUser = getById(user.getUserId());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        entityManager.flush();
    }

    @Override
    public boolean delete(int userId) {
        String hql = "DELETE FROM Users WHERE userId =:userId";
        int i = entityManager.createQuery(hql).setParameter("userId", userId).executeUpdate();
        return i != 0;
    }
}
