package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.validation.constraints.NotBlank;

public record FrainRelationJSON(
        @NotBlank(message = "Source ID is required")
        String sourceId,

        @NotBlank(message = "Target ID is required")
        String targetId,

        @NotBlank(message = "Relation description is required")
        String description,

        String technology
) {}
