package com.example.SpringBootNetTry.model;

import com.example.SpringBootNetTry.entity.StorageEntity;
import com.example.SpringBootNetTry.storage.StorageProperties;

import java.io.File;

public class StorageModel {

    //fields

    private String fileName;
    private String type;
    private long size;
    private File file;


    //methods

    public static StorageModel toStorageModel(StorageEntity entity){
        StorageModel model = new StorageModel();
        model.setFileName(entity.getName());
        model.setType(entity.getType());
        model.setSize(entity.getSize());
        model.setFile(new File("./uploads/"+entity.getName()));
        return model;
    }


    //constructors

    public StorageModel() {
    }


    //getters and setters


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
