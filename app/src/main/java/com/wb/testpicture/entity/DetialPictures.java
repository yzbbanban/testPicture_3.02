package com.wb.testpicture.entity;


public class DetialPictures {
    private String path;
    private int width;
    private int height;
    private long id;
    private String skipPagePath;


    public String getSkipPagePath() {
        return skipPagePath;
    }

    public void setSkipPagePath(String skipPagePath) {
        this.skipPagePath = skipPagePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
