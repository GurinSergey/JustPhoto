package com.gurin.core.dao;

import com.gurin.core.entities.Photo;

import java.util.List;

/**
 * Created by SGurin on 21.04.2016.
 */
public interface PhotoDao {
    public Photo findPhotoById(Integer id);

    public void updatePhoto(Photo photo);

    public List<Photo> getAllPhotosSortByDate();

    public List<Photo> getAllPhotosSortByPopularity();

    public List<Photo> getRandomPhotos(int cnt);

    public List<Photo> getPhotosByCategory(String cat);

    public List<Photo> getPhotosByCategoryAndPopularity(String cat);

    public List<Photo> getAllPhotosByUserIdSortByDate(int id);

    public double getAvgPointsAllPhotosByUserId(int id);

}
