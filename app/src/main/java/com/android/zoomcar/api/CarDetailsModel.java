package com.android.zoomcar.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 12-09-2015.
 */
public class CarDetailsModel {

    @Expose
    private List<Car> cars = new ArrayList<Car>();

    /**
     *
     * @return
     * The cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     *
     * @param cars
     * The cars
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public class Car {

        @Expose
        private String name;
        @Expose
        private String image;
        @Expose
        private String type;
        @SerializedName("hourly_rate")
        @Expose
        private String hourlyRate;
        @Expose
        private String rating;
        @Expose
        private String seater;
        @Expose
        private String ac;
        @Expose
        private Location location;

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         * The image
         */
        public String getImage() {
            return image;
        }

        /**
         *
         * @param image
         * The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         *
         * @return
         * The type
         */
        public String getType() {
            return type;
        }

        /**
         *
         * @param type
         * The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         *
         * @return
         * The hourlyRate
         */
        public String getHourlyRate() {
            return hourlyRate;
        }

        /**
         *
         * @param hourlyRate
         * The hourly_rate
         */
        public void setHourlyRate(String hourlyRate) {
            this.hourlyRate = hourlyRate;
        }

        /**
         *
         * @return
         * The rating
         */
        public String getRating() {
            return rating;
        }

        /**
         *
         * @param rating
         * The rating
         */
        public void setRating(String rating) {
            this.rating = rating;
        }

        /**
         *
         * @return
         * The seater
         */
        public String getSeater() {
            return seater;
        }

        /**
         *
         * @param seater
         * The seater
         */
        public void setSeater(String seater) {
            this.seater = seater;
        }

        /**
         *
         * @return
         * The ac
         */
        public String getAc() {
            return ac;
        }

        /**
         *
         * @param ac
         * The ac
         */
        public void setAc(String ac) {
            this.ac = ac;
        }

        /**
         *
         * @return
         * The location
         */
        public Location getLocation() {
            return location;
        }

        /**
         *
         * @param location
         * The location
         */
        public void setLocation(Location location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public class Location {

        @Expose
        private String latitude;
        @Expose
        private String longitude;

        /**
         * @return The latitude
         */
        public String getLatitude() {
            return latitude;
        }

        /**
         * @param latitude The latitude
         */
        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        /**
         * @return The longitude
         */
        public String getLongitude() {
            return longitude;
        }

        /**
         * @param longitude The longitude
         */
        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
