package com.apx.radiance.model;

public class NotificationItems {

    private String title, description, time;
    private Integer imageSource;

    public NotificationItems() {
    }

    public NotificationItems(String title, String description, String time, Integer imageSource) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.imageSource = imageSource;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public Integer getImageSource() {
        return imageSource;
    }

}
