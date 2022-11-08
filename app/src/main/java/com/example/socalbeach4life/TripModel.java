package com.example.socalbeach4life;

public class TripModel {
    public String tripid;
    public String dateAndTime;
    public String arrivalTime;
    public String beach;
    public ParkingLotModel parkingLotModel;

    public TripModel(String tripid, String dateAndTime, String arrivalTime, String beach, ParkingLotModel parkingLotModel) {
        this.tripid = tripid;
        this.dateAndTime = dateAndTime;
        this.arrivalTime = arrivalTime;
        this.beach = beach;
        this.parkingLotModel = parkingLotModel;
    }

    public TripModel() {
        this.tripid = "errortripid";
        this.dateAndTime = "errortripdateandtime";
        this.arrivalTime = "errorarrivaltime";
        this.beach = null;
        this.parkingLotModel = null;
    }

    public String getTripid() {
        return tripid;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getBeach() {
        return beach;
    }

    public ParkingLotModel getParkingLotModel() {
        return parkingLotModel;
    }
}
