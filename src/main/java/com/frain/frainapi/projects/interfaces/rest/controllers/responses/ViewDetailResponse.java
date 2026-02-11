package com.frain.frainapi.projects.interfaces.rest.controllers.responses;

import com.frain.frainapi.projects.domain.model.valueobjects.*;

import java.util.List;

/**
 * Full view response including nodes and relations.
 */
public record ViewDetailResponse(
        String id,
        ViewType type,
        String name,
        ContainerInfo container,
        List<FrainNodeJSON> nodes,
        List<FrainNodeJSON> externalNodes,
        List<FrainRelationJSON> relations
) {}
