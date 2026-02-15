package com.frain.frainapi.projects.application.queryservices;

import com.frain.frainapi.projects.domain.model.Project;
import com.frain.frainapi.projects.domain.model.queries.GetAllProjectsByOrganizationIdQuery;
import com.frain.frainapi.projects.domain.model.queries.GetProjectByIdQuery;
import com.frain.frainapi.projects.domain.services.ProjectQueryService;
import com.frain.frainapi.projects.infrastructure.repositories.ProjectRepository;
import com.frain.frainapi.projects.interfaces.rest.controllers.ProjectContextUtils;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectQueryServiceImpl implements ProjectQueryService {
    private final ProjectRepository projectRepository;
    private final ProjectContextUtils projectContextUtils;

    public ProjectQueryServiceImpl(ProjectRepository projectRepository, ProjectContextUtils projectContextUtils) {
        this.projectRepository = projectRepository;
        this.projectContextUtils = projectContextUtils;
    }


    @Override
    public Optional<Project> handle(GetProjectByIdQuery query) {
        return projectRepository.findById(query.projectId());
    }

    @Override
    public List<Project> handle(GetAllProjectsByOrganizationIdQuery query) {

        var isUserPartOfOrganization = projectContextUtils.isUserPartOfOrganization(query.userId(), query.organizationId().toString());

        if (!isUserPartOfOrganization) {
            throw new InsufficientPermissionsException("User does not have permissions to view projects in this organization.");
        }


        return projectRepository.findAllByOrganizationId(query.organizationId());
    }
}
