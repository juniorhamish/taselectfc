package com.taselectfc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String flagName;

    Team() {
        // Required for JSON deserialisation.
    }

    protected Team(Long id) {
        this.id = id;
    }

    private Team(String name, String flagName) {
        this.name = name;
        this.flagName = flagName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlagName() {
        return flagName;
    }

    public static class Builder {

        private String name;
        private String flagName;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder flagName(String flagName) {
            this.flagName = flagName;
            return this;
        }

        public Team build() {
            return new Team(name, flagName);
        }
    }

}