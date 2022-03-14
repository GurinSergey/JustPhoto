package com.gurin.core.dao.impl;

import com.gurin.core.dao.UserDao;
import com.gurin.core.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by SGurin on 24.03.2016.
 */
@Repository
public class IUserDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void createUser(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return em.merge(user);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        em.remove(user);
    }

    @Override
    public User findUserById(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            Query query = em.createQuery("select u from User u where u.email = ?1").setParameter(1, email);
            return (User) query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        Query query = em.createQuery("SELECT u FROM User u");
        return (List<User>) query.getResultList();
    }

}