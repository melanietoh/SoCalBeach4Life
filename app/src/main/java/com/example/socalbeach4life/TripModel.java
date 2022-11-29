package com.example.socalbeach4life;

import java.util.HashMap;

public class TripModel {
    public String tripid;
    public String dateAndTime;
    public String arrivalDateAndTime;
    public String mapsLink;
    public String beach;
    public ParkingLotModel parkingLotModel;
    public String restaurantName;

    public TripModel(String tripid, String dateAndTime, String arrivalDateAndTime, String mapsLink, String beach, ParkingLotModel parkingLotModel, String restaurantName) {
        this.tripid = tripid;
        this.dateAndTime = dateAndTime;
        this.arrivalDateAndTime = arrivalDateAndTime;
        this.mapsLink = mapsLink;
        this.beach = beach;
        this.parkingLotModel = parkingLotModel;
        this.restaurantName = restaurantName;
    }

    public TripModel() {
        this.tripid = "errortripid";
        this.dateAndTime = "errortripdateandtime";
        this.arrivalDateAndTime = "errorarrivaldateandtime";
        this.mapsLink = "errortripLink";
        this.beach = null;
        this.parkingLotModel = null;
        this.restaurantName = "***";
    }

    public String getTripid() {
        return tripid;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getMapsLink() {
        return mapsLink;
    }

    public String getBeach() {
        return beach;
    }

    public String getArrivalDateAndTime() {
        return arrivalDateAndTime;
    }

    public ParkingLotModel getParkingLotModel() {
        return parkingLotModel;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tripid", tripid);
        result.put("dateAndTime", dateAndTime);
        result.put("arrivalDateAndTime", arrivalDateAndTime);
        result.put("mapsLink", mapsLink);
        result.put("beach", beach);
        result.put("parkingLotModel", parkingLotModel);
        result.put("restaurantName", restaurantName);
        return result;
    }
}
