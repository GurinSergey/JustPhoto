package com.gurin.core.bo;

import com.gurin.core.dao.PhotoDao;
import com.gurin.core.entities.Photo;
import com.gurin.core.entities.User;
import com.gurin.web.customModel.FileUploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.gurin.utils.Const.SAVE_LOCATION;
import static com.gurin.utils.Utils.getAllCategories;

/**
 * Created by SGurin on 21.04.2016.
 */
@Service
public class PhotoService {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EvaluationService evaluationService;

    public void updatePhoto(Photo photo) {
        photoDao.updatePhoto(photo);
    }

    public Photo findPhotoById(int id) {
        return photoDao.findPhotoById(id);
    }

    public List<Photo> getAllPhotosSortByDate() {
        return photoDao.getAllPhotosSortByDate();
    }

    public List<Photo> getAllPhotosSortByPopularity() {
        return photoDao.getAllPhotosSortByPopularity();
    }

    public void savePhoto(FileUploadForm uploadForm, String email, String fileName) {
        User user = userService.findUserByEmail(email);
        String addLocation = email + "/";

        Photo photo = new Photo();
        photo.setPath(SAVE_LOCATION + addLocation + fileName);
        photo.setName(uploadForm.getName());
        photo.setDescription(uploadForm.getDescription());
        photo.setCategory(getAllCategories().get(uploadForm.getCategory()));
        photo.setCreateDate(new Timestamp((new Date()).getTime()));

        user.setPhoto(photo);

        userService.updateUser(user);
    }

    public void calculateTotalPointsByPhotoId(Photo photo) {
        photo.setTotalPoints(evaluationService.getTotalPointsByPhotoId(photo.getId()));
        updatePhoto(photo);
    }

    public void calculateTotalPointsByPhotoId(List<Photo> photos) {
        for (Photo photo : photos) {
            calculateTotalPointsByPhotoId(photo);
        }
    }

    public void hasPhotoAlreadyEvaluatedByCurrentUser(Photo photo) {
        photo.setAlreadyEvaluatedByCurrentUser(evaluationService.photoHasBeenEvaluatedByUserId(photo.getId()));
    }

    public List<Photo> getRandomPhotos(int cnt) {
        return photoDao.getRandomPhotos(cnt);
    }

    public List<Photo> getPhotosByCategory(String cat) {
        return photoDao.getPhotosByCategory(cat);
    }

    public List<Photo> getPhotosByCategoryAndPopularity(String cat) {
        return photoDao.getPhotosByCategoryAndPopularity(cat);
    }

    public List<Photo> getAllPhotosByUserIdSortByDate(int id) {
        return photoDao.getAllPhotosByUserIdSortByDate(id);
    }

    public double getAvgPointsAllPhotosByUserId(int id){
        return photoDao.getAvgPointsAllPhotosByUserId(id);
    }

}
