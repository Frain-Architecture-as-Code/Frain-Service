package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FrainViewJSON(
        @NotBlank(message = "View ID is required")
        String id,

        @NotNull(message = "View type is required")
        ViewType type,

        ContainerInfo container,

        @NotBlank(message = "View name is required")
        String name,

        @NotNull
        @Valid
        List<FrainNodeJSON> nodes,

        @NotNull
        @Valid
        List<FrainNodeJSON> externalNodes,

        @NotNull
        @Valid
        List<FrainRelationJSON> relations
) {}
