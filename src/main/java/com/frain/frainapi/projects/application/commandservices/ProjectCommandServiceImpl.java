package com.frain.frainapi.projects.application.commandservices;

import com.frain.frainapi.organizations.interfaces.acl.OrganizationContextFacade;
import com.frain.frainapi.projects.domain.exceptions.ProjectNotFoundException;
import com.frain.frainapi.projects.domain.model.Project;
import com.frain.frainapi.projects.domain.model.commands.CreateProjectCommand;
import com.frain.frainapi.projects.domain.model.commands.UpdateProjectVisibilityCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.domain.services.ProjectCommandService;
import com.frain.frainapi.projects.infrastructure.repositories.ProjectRepository;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectCommandServiceImpl implements ProjectCommandService {

    private final OrganizationContextFacade organizationContextFacade;
    private final ProjectRepository projectRepository;

    public ProjectCommandServiceImpl(OrganizationContextFacade organizationContextFacade, ProjectRepository projectRepository) {
        this.organizationContextFacade = organizationContextFacade;
        this.projectRepository = projectRepository;
    }


    @Override
    public ProjectId handle(CreateProjectCommand command) {

        var canCreateProject = organizationContextFacade.canCreateProjects(command.organizationId().toString(), command.userId().toString());

        if (!canCreateProject) {
            throw new InsufficientPermissionsException("User does not have permission to create projects in this organization.");
        }

        var projectId = ProjectId.generate();

        var project = new Project(projectId, command.organizationId(), command.visibility());

        projectRepository.save(project);
        return projectId;
    }

    @Override
    public ProjectId handle(UpdateProjectVisibilityCommand command) {
        var canUpdateProject = organizationContextFacade.canUpdateProject(command.organizationId().toString(), command.userId().toString());

        if (!canUpdateProject) {
            throw new InsufficientPermissionsException("User does not have permission to update projects in this organization.");
        }

        var project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new ProjectNotFoundException(command.projectId()));

        project.updateVisibility(command.visibility());

        projectRepository.save(project);

        return project.getId();
    }
}
