package com.frain.frainapi.projects.domain.services;

import com.frain.frainapi.projects.domain.model.Project;
import com.frain.frainapi.projects.domain.model.queries.GetAllProjectsByOrganizationIdQuery;
import com.frain.frainapi.projects.domain.model.queries.GetProjectByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryService {
    Optional<Project> handle(GetProjectByIdQuery query);
    List<Project> handle(GetAllProjectsByOrganizationIdQuery query);
}
