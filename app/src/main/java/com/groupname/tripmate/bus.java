package com.groupname.tripmate;

public class bus {
    private String busName;
    private String busNumber;
    private String time1;
    private String from1;
    private String to1;
    private String time2;
    private String from2;
    private String to2;

    public bus(String busName, String busNumber, String time1, String from1, String to1, String time2, String from2, String to2) {
        this.busName = busName;
        this.busNumber = busNumber;
        this.time1 = time1;
        this.from1 = from1;
        this.to1 = to1;
        this.time2 = time2;
        this.from2 = from2;
        this.to2 = to2;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getFrom1() {
        return from1;
    }

    public void setFrom1(String from1) {
        this.from1 = from1;
    }

    public String getTo1() {
        return to1;
    }

    public void setTo1(String to1) {
        this.to1 = to1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getFrom2() {
        return from2;
    }

    public void setFrom2(String from2) {
        this.from2 = from2;
    }

    public String getTo2() {
        return to2;
    }

    public void setTo2(String to2) {
        this.to2 = to2;
    }
}
