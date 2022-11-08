package com.example.socalbeach4life;

public class RestaurantModel {
    public String restaurantName;
    public String yelpLink;
    public String hours;
    public Double latitude;
    public Double longitude;
    public Double dist;

    public String getRestaurantName() { return restaurantName; }
    public String getYelpLink() { return yelpLink; }
    public String getHours() { return hours; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Double getDist() { return dist; }

    public RestaurantModel(String restaurantName, String yelpLink, String hours, Double latitude, Double longitude, Double dist) {
        this.restaurantName = restaurantName;
        this.yelpLink = yelpLink;
        this.hours = hours;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dist = dist;
    }
    public RestaurantModel() {
        restaurantName = "";
        yelpLink = "";
        hours = "";
        latitude = 0.0;
        longitude = 0.0;
        dist = 0.0;
    }
}
