package com.taselectfc.model;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Entity
public class Fixture {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Team homeTeam;
    @ManyToOne
    private Team awayTeam;
    private String venue;
    private ZonedDateTime kickoff;

    Fixture() {
        // Required for JSON deserialisation.
    }

    protected Fixture(Long id) {
        this.id = id;
    }

    private Fixture(Team homeTeam, Team awayTeam, String venue, ZonedDateTime kickOff) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.venue = venue;
        kickoff = kickOff;
    }

    public Long getId() {
        return id;
    }

    public String getVenue() {
        return venue;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public ZonedDateTime getKickoff() {
        return kickoff;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("homeTeam", homeTeam).add("awayTeam", awayTeam)
                .add("venue", venue).add("kickoff", kickoff).toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Fixture) {
            Fixture that = (Fixture) object;
            return Objects.equal(this.id, that.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static class Builder {

        private Team homeTeam;
        private Team awayTeam;
        private String venue;
        private ZonedDateTime kickoff;

        public Builder kickoff(ZonedDateTime kickoff) {
            this.kickoff = kickoff;

            return this;
        }

        public Builder homeTeam(Team homeTeam) {
            this.homeTeam = homeTeam;

            return this;
        }

        public Builder awayTeam(Team awayTeam) {
            this.awayTeam = awayTeam;

            return this;
        }

        public Builder venue(String venue) {
            this.venue = venue;

            return this;
        }

        public Fixture build() {
            return new Fixture(homeTeam, awayTeam, venue, kickoff);
        }

    }
}
