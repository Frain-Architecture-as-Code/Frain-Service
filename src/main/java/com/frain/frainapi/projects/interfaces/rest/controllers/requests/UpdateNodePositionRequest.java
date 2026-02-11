package com.frain.frainapi.projects.interfaces.rest.controllers.requests;

import jakarta.validation.constraints.NotNull;

/**
 * Request to update a node's position.
 */
public record UpdateNodePositionRequest(
        @NotNull(message = "X coordinate is required")
        Integer x,
        
        @NotNull(message = "Y coordinate is required")
        Integer y
) {}
