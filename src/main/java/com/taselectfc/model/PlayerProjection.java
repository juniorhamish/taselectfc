package com.taselectfc.model;

import java.time.LocalDate;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = Player.class)
public interface PlayerProjection {

    Long getId();

    String getFirstName();

    String getLastName();

    LocalDate getDateOfBirth();
}
