package com.android.zoomcar.api;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hp pc on 12-09-2015.
 */
public class BookingDetail extends RealmObject {
    @PrimaryKey
    private long id;
    private String carName;
    private String carImage;
    private String dateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
