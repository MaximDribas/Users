package com.dribas.dao;

import com.dribas.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class H2Storage implements UsersStorage {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAll() {
        String hql = "SELECT u FROM User u ORDER BY u.lastName";
        return entityManager.createQuery(hql, User.class).getResultList();
    }

    @Override
    public User getById(int userId) {
        String hql = "SELECT u FROM User u WHERE u.userId =:userId";
        return entityManager.createQuery(hql, User.class).setParameter("userId", userId).getSingleResult();
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
        String hql = "DELETE FROM User u  WHERE u.userId =:userId";
        int i = entityManager.createQuery(hql).setParameter("userId", userId).executeUpdate();
        return i != 0;
    }
}
