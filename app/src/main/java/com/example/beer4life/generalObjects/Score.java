package com.example.beer4life.generalObjects;

public class Score {
    private int score;
    private String time;
    private double lat;
    private double lon;
    private String address;

    public Score() { }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Score setTime(String time) {
        this.time = time;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Score setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Score setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Score setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", time='" + time + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", address='" + address + '\'' +
                '}';
    }
}
