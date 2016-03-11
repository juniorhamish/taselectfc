package com.taselectfc.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = Team.class)
public interface TeamProjection {

    Long getId();

    String getName();

    String getFlagName();

}
