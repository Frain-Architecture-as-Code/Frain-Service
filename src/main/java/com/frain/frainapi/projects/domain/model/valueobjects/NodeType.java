package com.frain.frainapi.projects.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NodeType {
    @JsonProperty("PERSON")
    PERSON,

    @JsonProperty("SYSTEM")
    SYSTEM,

    @JsonProperty("EXTERNAL_SYSTEM")
    EXTERNAL_SYSTEM,

    @JsonProperty("DATABASE")
    DATABASE,

    @JsonProperty("WEB_APP")
    WEB_APP,

    @JsonProperty("CONTAINER")
    CONTAINER,

    @JsonProperty("COMPONENT")
    COMPONENT
}
