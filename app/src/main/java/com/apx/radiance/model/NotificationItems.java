package com.apx.radiance.model;

public class NotificationItems {

    private String notificationId;
    private String title, description, time;
    private Integer imageSource;

    public NotificationItems() {
    }

    public NotificationItems(String notificationId, String title, String description, String time, Integer imageSource) {
        this.notificationId = notificationId;
        this.title = title;
        this.description = description;
        this.time = time;
        this.imageSource = imageSource;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImageSource(Integer imageSource) {
        this.imageSource = imageSource;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
