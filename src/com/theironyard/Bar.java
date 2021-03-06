package com.theironyard;

/**
 * Created by Erik on 6/16/16.
 */
public class Bar {
    Integer barId;
    String barName;
    String barLocation;
    String imageUrl;
    String author;

    public Bar(Integer barId, String barName, String barLocation, String imageUrl, String author) {
        this.barId = barId;
        this.barName = barName;
        this.barLocation = barLocation;
        this.imageUrl = imageUrl;
        this.author = author;
    }

    public Bar() {
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getBarLocation() {
        return barLocation;
    }

    public void setBarLocation(String barLocation) {
        this.barLocation = barLocation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
