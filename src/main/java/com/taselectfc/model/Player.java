package com.taselectfc.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    protected Player(Long id) {
        this.id = id;
    }

    private Player(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return this.id;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public LocalDate dateOfBirth() {
        return this.dateOfBirth;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("firstName", firstName).add("lastName", lastName)
                .add("dateOfBirth", dateOfBirth).toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Player) {
            Player that = (Player) object;
            return Objects.equal(this.id, that.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Player build() {
            return new Player(firstName, lastName, dateOfBirth);
        }
    }

}
