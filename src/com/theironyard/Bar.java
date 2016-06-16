package com.theironyard;

/**
 * Created by Erik on 6/16/16.
 */
public class Bar {
    Integer barId;
    String barName;
    String barLocation;

    public Bar(Integer barId, String barName, String barLocation) {
        this.barId = barId;
        this.barName = barName;
        this.barLocation = barLocation;
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
}
