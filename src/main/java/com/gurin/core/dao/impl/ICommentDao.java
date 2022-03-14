package com.gurin.core.dao.impl;

import com.gurin.core.dao.CommentDao;
import com.gurin.core.entities.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by SGurin on 22.04.2016.
 */

@Repository
public class ICommentDao implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addComment(Comment comment) {
        em.persist(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public List<Comment> findAllCommentByPhotoId(int id) {
        Query query = em.createQuery("SELECT c FROM Comment c where c.photo.id = ?1 and c.parent is null").setParameter(1, id);
        return (List<Comment>) query.getResultList();
    }

    @Override
    public Comment findCommentById(int id) {
        Comment comment = null;
        try {
            Query query = em.createQuery("SELECT c FROM Comment c where c.id = ?1").setParameter(1, id);
            comment = (Comment) query.getSingleResult();
        } finally {
            return comment;
        }
    }

}
