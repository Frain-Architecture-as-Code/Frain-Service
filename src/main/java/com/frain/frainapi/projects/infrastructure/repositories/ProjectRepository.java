package com.frain.frainapi.projects.infrastructure.repositories;

import com.frain.frainapi.projects.domain.model.Project;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, ProjectId> {
    List<Project> findAllByOrganizationId(OrganizationId organizationId);
}
