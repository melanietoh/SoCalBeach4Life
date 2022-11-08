package com.example.socalbeach4life;

public class RestaurantModel {
    public String restaurantName;
    public String yelpLink;

    public String getRestaurantName() { return restaurantName; }
    public String getYelpLink() { return yelpLink; }

    public RestaurantModel(String restaurantName, String yelpLink) {
        this.restaurantName = restaurantName;
        this.yelpLink = yelpLink;
    }
    public RestaurantModel() {
        restaurantName = "";
        yelpLink = "";
    }
}
