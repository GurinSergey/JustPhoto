package com.gurin.core.bo;

import com.gurin.core.dao.EvaluationDao;
import com.gurin.core.entities.Evaluation;
import com.gurin.web.jsonmodel.JsonEvaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

import static com.gurin.utils.Utils.getCurrentUser;

/**
 * Created by SGurin on 04.05.2016.
 */
@Service
public class EvaluationService {

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private EvaluationDao evaluationDao;

    public void addEvaluation(Evaluation evaluation) {
        evaluationDao.addEvaluation(evaluation);
    }

    public void addEvaluationByJson(JsonEvaluation jsonObject) {
        Evaluation evaluation = new Evaluation();

        evaluation.setPoints(jsonObject.getPoints());
        evaluation.setCreateDate(new Timestamp((new Date()).getTime()));

        evaluation.setUser(userService.findUserById(getCurrentUser().getId()));
        evaluation.setPhoto(photoService.findPhotoById(jsonObject.getPhoto_id()));

        addEvaluation(evaluation);
    }

    public double getTotalPointsByPhotoId(int id) {
        return evaluationDao.getTotalPointsByPhotoId(id);
    }

    public boolean photoHasBeenEvaluatedByUserId(int id) {
        return evaluationDao.photoHasBeenEvaluatedByUserId(id);
    }

}
