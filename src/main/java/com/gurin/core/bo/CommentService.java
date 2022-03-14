package com.gurin.core.bo;

import com.gurin.core.dao.CommentDao;
import com.gurin.core.entities.Comment;
import com.gurin.web.jsonmodel.JsonComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by SGurin on 22.04.2016.
 */
@Service
public class CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentDao commentDao;

    public void addComment(Comment comment) {
        commentDao.addComment(comment);
    }

    public Comment updateComment(Comment comment) {
        return commentDao.updateComment(comment);
    }

    public List<Comment> findAllCommentByPhotoId(int id) {
        return commentDao.findAllCommentByPhotoId(id);
    }

    public Comment findCommentById(int id) {
        return commentDao.findCommentById(id);
    }

    public Comment addCommentByJson(JsonComment jsonObject) {
        Comment addedComment = new Comment();
        addedComment.setContent(jsonObject.getContent());
        addedComment.setParent(findCommentById(jsonObject.getParent_id()));
        addedComment.setCreateDate(new Timestamp((new Date()).getTime()));
        addedComment.setUser(userService.findUserById(jsonObject.getUser_id()));
        addedComment.setPhoto(photoService.findPhotoById(jsonObject.getPhoto_id()));
        addedComment = updateComment(addedComment);

        if (addedComment.getParent() != null) {
            Comment parentComment = findCommentById(addedComment.getParent().getId());
            parentComment.setChildren(addedComment);

            updateComment(parentComment);
        }

        return addedComment;
    }

//    public int getOrderValueByCommentId(int id) {
//        int orderVal = -1;
//        try {
//            Comment comment = findCommentById(id);
//            orderVal = comment.getChildren().size();
//        } catch (Exception e) {
//        }
//        return orderVal;
//    }
}
