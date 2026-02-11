package com.frain.frainapi.projects.interfaces.rest.controllers.responses;

import com.frain.frainapi.projects.domain.model.valueobjects.C4Model;

/**
 * Response containing the full C4Model.
 */
public record C4ModelResponse(
        String projectId,
        C4Model c4Model
) {}
