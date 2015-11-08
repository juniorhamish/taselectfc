package com.taselectfc.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Fixture {

    private String id;
    private Date date;
    private String homeTeamName;
    private String awayTeamName;
    private String homeTeamFlag;
    private String awayTeamFlag;
    private String venue;

    public Fixture() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHomeTeamFlag() {
        return homeTeamFlag;
    }

    public void setHomeTeamFlag(String homeTeamFlag) {
        this.homeTeamFlag = homeTeamFlag;
    }

    public String getAwayTeamFlag() {
        return awayTeamFlag;
    }

    public void setAwayTeamFlag(String awayTeamFlag) {
        this.awayTeamFlag = awayTeamFlag;
    }

    @Override
    public String toString() {
        return id + " - " + homeTeamName + " v " + awayTeamName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Fixture)) {
            return false;
        }

        Fixture other = (Fixture) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
