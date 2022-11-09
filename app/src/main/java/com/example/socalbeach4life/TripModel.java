package com.example.socalbeach4life;

import java.util.HashMap;

public class TripModel {
    public String tripid;
    public String dateAndTime;
    public String mapsLink;
    public String beach;
    public ParkingLotModel parkingLotModel;

    public TripModel(String tripid, String dateAndTime, String arrivalTime, String beach, ParkingLotModel parkingLotModel) {
        this.tripid = tripid;
        this.dateAndTime = dateAndTime;
        this.mapsLink = arrivalTime;
        this.beach = beach;
        this.parkingLotModel = parkingLotModel;
    }

    public TripModel() {
        this.tripid = "errortripid";
        this.dateAndTime = "errortripdateandtime";
        this.mapsLink = "errortripLink";
        this.beach = null;
        this.parkingLotModel = null;
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

    public ParkingLotModel getParkingLotModel() {
        return parkingLotModel;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tripid", tripid);
        result.put("dateAndTime", dateAndTime);
        result.put("mapsLink", mapsLink);
        result.put("beach", beach);
        result.put("parkingLotModel", parkingLotModel);
        return result;
    }
}
