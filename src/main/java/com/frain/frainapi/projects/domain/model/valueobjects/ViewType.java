package com.frain.frainapi.projects.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ViewType {
    @JsonProperty("CONTEXT")
    CONTEXT,

    @JsonProperty("CONTAINER")
    CONTAINER,

    @JsonProperty("COMPONENT")
    COMPONENT
}
