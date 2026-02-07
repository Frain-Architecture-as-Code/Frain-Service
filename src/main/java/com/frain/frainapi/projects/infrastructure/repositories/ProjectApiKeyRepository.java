package com.frain.frainapi.projects.infrastructure.repositories;

import com.frain.frainapi.projects.domain.model.ProjectApiKey;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectApiKeyRepository extends JpaRepository<ProjectApiKey, ProjectApiKeyId> {
}
