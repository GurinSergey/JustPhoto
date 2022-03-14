package com.gurin.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurin.core.bo.CommentService;
import com.gurin.core.bo.EvaluationService;
import com.gurin.core.bo.PhotoService;
import com.gurin.core.bo.UserService;
import com.gurin.core.entities.Comment;
import com.gurin.core.entities.UserInfo;
import com.gurin.web.jsonmodel.JsonComment;
import com.gurin.web.jsonmodel.JsonEvaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gurin.core.entities.User;

import java.io.IOException;
import java.util.List;

import static com.gurin.utils.Utils.getCurrentUser;

/**
 * Created by SGurin on 27.04.2016.
 */

@RestController
public class RestControllers {

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestBody JsonComment jsonObject) throws IOException {
        Comment addedComment = commentService.addCommentByJson(jsonObject);

//        int orderValue = commentService.getOrderValueByCommentId(addedComment.getParent().getId());
//        addedComment.setChildOrder(orderValue);

        return new ObjectMapper().writeValueAsString(addedComment);
    }

    @RequestMapping(value = "/drawComments", method = RequestMethod.POST)
    @ResponseBody
    public List<Comment> drawComments(@RequestParam(value = "id") String id) throws IOException {
        List<Comment> allCommentsByPhotoId = commentService.findAllCommentByPhotoId(Integer.parseInt(id));

        return allCommentsByPhotoId;
    }

    @RequestMapping(value = "/evaluation", method = RequestMethod.POST)
    @ResponseBody
    public double getEvaluation(@RequestBody JsonEvaluation jsonObject) throws IOException {
        evaluationService.addEvaluationByJson(jsonObject);
        return evaluationService.getTotalPointsByPhotoId(jsonObject.getPhoto_id());
    }

    @RequestMapping(value = "/getProfileURL", method = RequestMethod.POST)
    @ResponseBody
    public int getUserPrincipalId() throws IOException {
        return getCurrentUser().getId();
    }

    @RequestMapping(value = "/deleteAvatar", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteAvatar(@RequestParam(value = "id") Integer id) throws IOException {
        User user = userService.findUserById(id);
        UserInfo userInfo = user.getUserInfo();
        userInfo.setPathAvatar("");

        return userService.updateUser(user);
    }

}
