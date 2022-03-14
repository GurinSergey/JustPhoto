package com.gurin.core.dao.impl;

import com.gurin.core.dao.UserInfoDao;
import com.gurin.core.entities.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by SGurin on 07.06.2016.
 */
@Repository
public class IUserInfoDao implements UserInfoDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserInfo updateUserInfo(UserInfo userInfo) {
        return em.merge(userInfo);
    }
}
