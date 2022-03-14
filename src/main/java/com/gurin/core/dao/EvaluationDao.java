package com.gurin.core.dao;

import com.gurin.core.entities.Evaluation;

/**
 * Created by SGurin on 04.05.2016.
 */
public interface EvaluationDao {
    public void addEvaluation(Evaluation evaluation);

    public double getTotalPointsByPhotoId(int id);

    public boolean photoHasBeenEvaluatedByUserId(int photo_id);
}
