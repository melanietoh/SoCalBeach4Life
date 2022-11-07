package com.example.socalbeach4life;

public class ReviewModel {
    public String username;
    public String displayName;
    public String message;
    public Double rating;

    public ReviewModel(String username, String displayName, String message, Double rating) {
        this.username = username;
        this.displayName = displayName;
        this.message = message;
        this.rating = rating;
    }

    public ReviewModel() {
        this.username = "error";
        this.displayName = "error";
        this.message = "error";
        this.rating = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMessage() {
        return message;
    }

    public Double getRating() {
        return rating;
    }
}
