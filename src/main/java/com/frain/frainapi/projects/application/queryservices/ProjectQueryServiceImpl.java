package com.frain.frainapi.projects.application.queryservices;

import com.frain.frainapi.projects.domain.model.Project;
import com.frain.frainapi.projects.domain.model.queries.GetAllProjectsByOrganizationIdQuery;
import com.frain.frainapi.projects.domain.model.queries.GetProjectByIdQuery;
import com.frain.frainapi.projects.domain.services.ProjectQueryService;
import com.frain.frainapi.projects.infrastructure.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectQueryServiceImpl implements ProjectQueryService {
    private final ProjectRepository projectRepository;

    public ProjectQueryServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public Optional<Project> handle(GetProjectByIdQuery query) {
        return projectRepository.findById(query.projectId());
    }

    @Override
    public List<Project> handle(GetAllProjectsByOrganizationIdQuery query) {
        return projectRepository.findAllByOrganizationId(query.organizationId());
    }
}
