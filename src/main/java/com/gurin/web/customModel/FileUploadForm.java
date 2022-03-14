package com.gurin.web.customModel;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by SGurin on 08.04.2016.
 */
public class FileUploadForm {
    private MultipartFile file;

    private String name;

    private String description;

    private int category;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
