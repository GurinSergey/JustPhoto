package com.gurin.core.run;


import com.gurin.core.bo.*;
import com.gurin.core.config.PersistenceJPAConfig;
import com.gurin.core.entities.User;
import com.gurin.core.entities.UserInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;

/**
 * Created by SGurin on 24.03.2016.
 */
public class App {

    public static void main(String[] args) throws ParseException {


/*        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class);

        UserBo userBo = applicationContext.getBean(UserBo.class);

        User user = new User();
        user.setName("john");
        user.setPassword("john");
        user.setEmail("john@gmail.ru");
        user.setRole(Roles.ADMIN.name());


        Photo photo = new Photo();
        photo.setPath("photo3");

        user.setPhoto(photo);

        userBo.createUser(user);

        User user = userDao.findUserById(1);

        Photo photo2 = new Photo();
        photo2.setPath("photo2");

        user.setPhoto(photo2);

        userDao.updateUser(user);
*/

        //System.out.println(usersDao.findUserByEmail("sdf"));

//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class);
//
//
//        UserService userService = applicationContext.getBean(UserService.class);
//
//        User u = userService.findUserById(1);
//        System.out.println(u.getId());

//        AtomicInteger atomicInteger = new AtomicInteger();
//        atomicInteger.incrementAndGet();
//
//        System.out.println(atomicInteger);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class);

        CommentService commentService = applicationContext.getBean(CommentService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        PhotoService photoService = applicationContext.getBean(PhotoService.class);
        EvaluationService evaluationService = applicationContext.getBean(EvaluationService.class);
        UserInfoService userInfoService = applicationContext.getBean(UserInfoService.class);

//        Comment comment = new Comment();
//        comment.setContent("тест");
//        comment.setUser(userService.findUserById(1));
//        comment.setPhoto(photoService.findPhotoById(1));
//        comment.setCreateDate(new Timestamp((new Date()).getTime()));
//
//        commentService.addComment(comment);

//
//Comment parentComment = commentService.findCommentById(12);
//
//        Comment childComment = new Comment();
//        childComment.setContent("тест6");
//        childComment.setUser(userService.findUserById(1));
//        childComment.setPhoto(photoService.findPhotoById(1));
//        childComment.setCreateDate(new Timestamp((new Date()).getTime()));
//        childComment.setParent(parentComment);
//
//        childComment = commentService.updateComment(childComment);
//
//        parentComment.setChildren(childComment);
//        commentService.updateComment(parentComment);

//        Evaluation evaluation = new Evaluation();
//        evaluation.setUser(userService.findUserById(1));
//        evaluation.setPhoto(photoService.findPhotoById(1));
//        evaluation.setCreateDate(new Timestamp((new Date()).getTime()));
//        evaluation.setPoints(5);
//
//        evaluationService.addEvaluation(evaluation);

//1989-05-31

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        sdf.setLenient(false);
//        Date dt2 = sdf.parse("1989-05-31");
//        System.out.println(dt2);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//
//        System.out.println(dateFormat.parse("31.05.1989"));

        User user = userService.findUserById(1);
//        UserInfo userInfo =  userInfoService.findInfoByUserId(user.getId());
        UserInfo userInfo = new UserInfo();

        userInfo.setTown("Kiev");
        userInfo.setPathAvatar("/C:");
        userInfo.setCountry("USA1");
        //userInfo.setUser(user);

        //userInfoService.updateUserInfo(userInfo);

        user.setUserInfo(userInfo);
        userService.updateUser(user);
    }
}
