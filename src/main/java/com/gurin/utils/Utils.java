package com.gurin.utils;

import com.gurin.core.entities.Photo;
import com.gurin.core.entities.User;
import com.gurin.core.entities.UserInfo;
import com.gurin.core.entities.enums.Categories;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by SGurin on 15.04.2016.
 */
public class Utils {

    public static String getRandomPhotoName() {
        return UUID.randomUUID().toString().replaceAll("-", "") + '_';
    }

    public static User getCurrentUser() {
        User currentUser = null;
        try {
            currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return currentUser;
        }
        return currentUser;
    }

    public static boolean isCurrentUser(User user) {
        try {
            if (getCurrentUser().getEmail().equals(user.getEmail())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void encodedUserInfoAvatar(UserInfo userInfo) throws IOException {
        String encoded = "";

        if (!isEmpty(userInfo.getPathAvatar())) {
            File file = new File(userInfo.getPathAvatar());

            InputStream inputStream = new FileInputStream(file);
            byte bytearray[] = IOUtils.toByteArray(inputStream);
            encoded = Base64.getEncoder().encodeToString(bytearray);
        }
        userInfo.setEncoded(encoded);
    }

    public static void encodedPhotoByPath(Photo photo) throws IOException {
        File file = new File(photo.getPath());

        InputStream inputStream = new FileInputStream(file);
        byte bytearray[] = IOUtils.toByteArray(inputStream);
        String encoded = Base64.getEncoder().encodeToString(bytearray);

        photo.setEncoded(encoded);
    }

    public static void encodedPhotoByPath(List<Photo> photos) throws IOException {
        for (Photo photo : photos) {
            encodedPhotoByPath(photo);
        }
    }

    public static Map<Integer, String> getAllCategories() {
        Map<Integer, String> allCategories = new HashMap<>();

        for (Categories cat : Categories.values()) {
            allCategories.put(cat.getIndex(), cat.getName());
        }
        return allCategories;
    }

    public static Map<Integer, String> getAllCategoriesWithAll() {
        Map<Integer, String> allCategories = getAllCategories();
        allCategories.put(allCategories.size(), "Все");

        return allCategories;
    }

    public static int getIdCategoryByValue(String value) {
        int key = 0;
        for (Map.Entry<Integer, String> entity : getAllCategoriesWithAll().entrySet()) {
            if (entity.getValue().equals(value)) {
                key = entity.getKey();
                break;
            }
        }
        return key;
    }

    public static boolean isEmpty(Object val) {
        return val == null || val.equals("");
    }
}
