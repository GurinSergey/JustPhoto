package com.gurin.core.bo;

import com.gurin.core.dao.UserInfoDao;
import com.gurin.core.entities.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SGurin on 07.06.2016.
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    public boolean updateUserInfo(UserInfo userInfo) {
        return userInfoDao.updateUserInfo(userInfo).getClass() == UserInfo.class;
    }

}
