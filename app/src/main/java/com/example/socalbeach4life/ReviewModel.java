package com.example.socalbeach4life;

import java.util.HashMap;

public class ReviewModel {
    public String id;
    public String uid;
    public String beachName;
    public String displayName;
    public String message;
    public Double rating;

    public ReviewModel(String id, String uid, String beachName, String displayName, String message, Double rating) {
        this.id = id;
        this.uid = uid;
        this.beachName = beachName;
        this.displayName = displayName;
        this.message = message;
        this.rating = rating;
    }

    public ReviewModel() {
        this.id = "0";
        this.uid = "erroruidReview";
        this.beachName = "errorbeachNameReview";
        this.displayName = "error";
        this.message = "error";
        this.rating = 0.0;
    }

    public String getId() { return id;}

    public String getUid() {
        return uid;
    }

    public String getBeachName() {
        return beachName;
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

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("uid", uid);
        result.put("beachName", beachName);
        result.put("displayName", displayName);
        result.put("message", message);
        result.put("rating", rating);
        return result;
    }

}
