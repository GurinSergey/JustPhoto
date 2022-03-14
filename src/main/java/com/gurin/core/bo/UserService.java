package com.gurin.core.bo;

import com.gurin.core.dao.UserDao;
import com.gurin.core.entities.User;
import com.gurin.core.entities.UserInfo;
import com.gurin.web.customModel.ProfileForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

import static com.gurin.utils.Const.SAVE_LOCATION;
import static com.gurin.utils.service.FileServiceUtils.saveFile;

/**
 * Created by SGurin on 29.03.2016.
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInfoService userInfoService;

    public void createUser(User user) {
        userDao.createUser(user);
    }

    public boolean updateUser(User user) {
        return userDao.updateUser(user).getClass() == User.class;
    }

    public void removeUser(User user) {
        userDao.removeUser(user);
    }

    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
        Set<GrantedAuthority> role = new HashSet<>();
        role.add(new SimpleGrantedAuthority(user.getRole()));

        user.setAuthorities(role);

        return user;

//       UserDetails userDetails = new User(user.getEmail(), user.getPassword(), role);
//        return userDetails;
    }

    public UserInfo fillUserInfo(UserInfo userInfo, ProfileForm profileForm, User user) {
        try {
            userInfo.setCountry(profileForm.getCountry());
            userInfo.setTown(profileForm.getTown());
            userInfo.setBirthday(profileForm.getBirthday());
            userInfo.setAboutMyself(profileForm.getAboutMyself());

            if (profileForm.getFile().getSize() > 0) {
                MultipartFile multipartFile = profileForm.getFile();
                saveFile(multipartFile, user.getEmail(), "avatar");
                userInfo.setPathAvatar(SAVE_LOCATION + user.getEmail() + "/avatar/" + multipartFile.getOriginalFilename());
            }

            return userInfo;

        } catch (Exception e) {
            return userInfo;
        }
    }

    private User fillUser(ProfileForm profileForm, User user) {
        user.setUsername(profileForm.getName());
        user.setEmail(profileForm.getEmail());

        return user;
    }

    public boolean changeProfile(User user, ProfileForm profileForm) {
        try {
            UserInfo userInfo = (user.getUserInfo() == null) ? new UserInfo() : user.getUserInfo();
            userInfo = fillUserInfo(userInfo, profileForm, user);
            user = fillUser(profileForm, user);

            userInfo.setUser(user);
            user.setUserInfo(userInfo);

            updateUser(user);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}