package com.gurin.utils.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.gurin.utils.Const.SAVE_LOCATION;

/**
 * Created by SGurin on 11.04.2016.
 */
@Service
public class FileServiceUtils {

    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File f = new File(dir, children[i]);
                deleteDirectory(f);
            }
            dir.delete();
        } else dir.delete();
    }

    public static boolean saveFile(MultipartFile multipartFile, String email, String prefix) {
        boolean result = false;
        String fileName = "";
        if (prefix.equals("avatar")) {
            fileName = multipartFile.getOriginalFilename();
        } else {
            fileName = prefix + multipartFile.getOriginalFilename();
        }

        String location = SAVE_LOCATION;
        String addLocation = email + "/";
        String avatarLocation = prefix + "/";
        File pathFile = new File(location);
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }

        File pathFilePersonal = new File(location + addLocation);
        if (!pathFilePersonal.exists()) {
            pathFilePersonal.mkdir();
        }

        if (prefix.equals("avatar")) {
            File pathFilePersonalAvatar = new File(location + addLocation + avatarLocation);
            deleteDirectory(pathFilePersonalAvatar);
            if (!pathFilePersonalAvatar.exists()) {
                pathFilePersonalAvatar.mkdir();
            }
        }
        if (prefix.equals("avatar")) {
            pathFile = new File(location + addLocation + avatarLocation + fileName);
        } else {
            pathFile = new File(location + addLocation + fileName);
        }
        try {
            multipartFile.transferTo(pathFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}