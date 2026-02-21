package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FrainNodeJSON(
        @NotBlank(message = "Node ID is required")
        String id,

        @NotNull(message = "Node type is required")
        NodeType type,

        @NotBlank(message = "Node name is required")
        String name,

        @NotBlank(message = "Node description is required")
        String description,

        @NotBlank(message = "Node technology is required")
        String technology,

        String viewId,

        int x,
        int y
) {}
