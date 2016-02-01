package com.taselectfc.model;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Fixture {

    @Id
    private String id;
    private ZonedDateTime kickoff;
    @ManyToOne
    private Team homeTeam;
    @ManyToOne
    private Team awayTeam;
    private String venue;

    protected Fixture() {
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

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public ZonedDateTime getKickoff() {
        return kickoff;
    }

    public void setKickoff(ZonedDateTime date) {
        this.kickoff = date;
    }

    @Override
    public String toString() {
        return id + " - " + homeTeam + " v " + awayTeam;
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
