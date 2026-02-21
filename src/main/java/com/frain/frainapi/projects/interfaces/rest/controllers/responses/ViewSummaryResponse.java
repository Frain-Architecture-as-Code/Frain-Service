package com.frain.frainapi.projects.interfaces.rest.controllers.responses;

import com.frain.frainapi.projects.domain.model.valueobjects.ContainerInfo;
import com.frain.frainapi.projects.domain.model.valueobjects.ViewType;

/**
 * Summary response for a view (without nodes and relations).
 */
public record ViewSummaryResponse(
        String id,
        ViewType type,
        String name,
        ContainerInfo container
) {}
