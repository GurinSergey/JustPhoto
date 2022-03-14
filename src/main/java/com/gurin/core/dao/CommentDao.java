package com.gurin.core.dao;

import com.gurin.core.entities.Comment;

import java.util.List;

/**
 * Created by SGurin on 22.04.2016.
 */

public interface CommentDao {
    public void addComment(Comment comment);

    public Comment updateComment(Comment comment);

    public List<Comment> findAllCommentByPhotoId(int id);

    public Comment findCommentById(int id);

}

