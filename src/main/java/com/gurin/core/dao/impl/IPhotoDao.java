package com.gurin.core.dao.impl;

import com.gurin.core.dao.PhotoDao;
import com.gurin.core.entities.Photo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by SGurin on 21.04.2016.
 */

@Repository
public class IPhotoDao implements PhotoDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void updatePhoto(Photo photo) {
        em.merge(photo);
    }

    @Override
    public Photo findPhotoById(Integer id) {
        return em.find(Photo.class, id);
    }

    @Override
    public List<Photo> getAllPhotosSortByDate() {
        Query query = em.createQuery("SELECT p FROM Photo p order by p.createDate DESC");
        return (List<Photo>) query.getResultList();
    }

    @Override
    public List<Photo> getAllPhotosSortByPopularity() {
        Query query = em.createQuery("SELECT p FROM Photo p order by p.totalPoints DESC");
        return (List<Photo>) query.getResultList();
    }

    @Override
    public List<Photo> getRandomPhotos(int cnt) {
        Query query = em.createQuery("SELECT p FROM Photo p ORDER BY RAND()").setMaxResults(cnt);
        return (List<Photo>) query.getResultList();
    }

    @Override
    public List<Photo> getPhotosByCategory(String cat) {
        Query query = em.createQuery("SELECT p FROM Photo p where p.category = ?1").setParameter(1, cat);
        return (List<Photo>) query.getResultList();
    }

    @Override
    public List<Photo> getPhotosByCategoryAndPopularity(String cat) {
        Query query = em.createQuery("SELECT p FROM Photo p where p.category = ?1 order by p.totalPoints DESC").setParameter(1, cat);
        return (List<Photo>) query.getResultList();
    }

    @Override
    public List<Photo> getAllPhotosByUserIdSortByDate(int id) {
        Query query = em.createQuery("SELECT p FROM Photo p where p.user.id = ?1 order by p.createDate DESC").setParameter(1, id);
        return (List<Photo>) query.getResultList();
    }

    @Override
    public double getAvgPointsAllPhotosByUserId(int id) {
        double amount = 0.00;
        try {
            Query query = em.createQuery("SELECT AVG(p.totalPoints) FROM Photo p where p.user.id = ?1").setParameter(1, id);
            amount = (double) query.getSingleResult();

        } catch (Exception e) {
            return amount;
        }
        return (double) Math.round(amount * 100) / 100;
    }

}
