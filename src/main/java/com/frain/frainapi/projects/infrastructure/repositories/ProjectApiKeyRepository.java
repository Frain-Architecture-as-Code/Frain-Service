package com.frain.frainapi.projects.infrastructure.repositories;

import com.frain.frainapi.projects.domain.model.ProjectApiKey;
import com.frain.frainapi.projects.domain.model.valueobjects.ApiKey;
import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectApiKeyRepository extends JpaRepository<ProjectApiKey, ProjectApiKeyId> {
    
    Optional<ProjectApiKey> findByApiKey(ApiKey apiKey);
    
    Optional<ProjectApiKey> findByProjectIdAndMemberId(ProjectId projectId, MemberId memberId);
    
    boolean existsByProjectIdAndMemberId(ProjectId projectId, MemberId memberId);
    
    List<ProjectApiKey> findAllByProjectId(ProjectId projectId);
}
