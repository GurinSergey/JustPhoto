package com.gurin.core.dao.impl;

import com.gurin.core.dao.EvaluationDao;
import com.gurin.core.entities.Evaluation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import static com.gurin.utils.Utils.getCurrentUser;

/**
 * Created by SGurin on 04.05.2016.
 */

@Repository
public class IEvaluationDao implements EvaluationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addEvaluation(Evaluation evaluation) {
        em.persist(evaluation);
    }

    @Override
    public double getTotalPointsByPhotoId(int id) {
        double amount = 0.00;
        try {
            Query query = em.createQuery("SELECT SUM(e.points) FROM Evaluation e where e.photo.id = ?1").setParameter(1, id);
            amount = (double) query.getSingleResult();

        } catch (Exception e) {
            return amount;
        }
        return (double) Math.round(amount * 100) / 100;
    }

    @Override
    public boolean photoHasBeenEvaluatedByUserId(int photo_id) {
        boolean result = false;
        try {
            Query query = em.createQuery("select 1 from Evaluation u where u.photo.id = ?1 and u.user.id = ?2")
                    .setParameter(1, photo_id)
                    .setParameter(2, getCurrentUser().getId());
            result = ((int)query.getSingleResult() == 1);
        } catch (Exception ex) {
            return false;
        }
        return result;
    }
}
