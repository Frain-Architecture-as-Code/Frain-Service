package com.frain.frainapi.projects.application.queryservices;

import com.frain.frainapi.projects.domain.model.ProjectApiKey;
import com.frain.frainapi.projects.domain.model.queries.GetProjectApiKeyByKeyQuery;
import com.frain.frainapi.projects.domain.model.queries.GetProjectApiKeysQuery;
import com.frain.frainapi.projects.domain.services.ProjectApiKeyQueryService;
import com.frain.frainapi.projects.infrastructure.repositories.ProjectApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectApiKeyQueryServiceImpl implements ProjectApiKeyQueryService {

    private final ProjectApiKeyRepository projectApiKeyRepository;

    public ProjectApiKeyQueryServiceImpl(ProjectApiKeyRepository projectApiKeyRepository) {
        this.projectApiKeyRepository = projectApiKeyRepository;
    }

    @Override
    public Optional<ProjectApiKey> handle(GetProjectApiKeyByKeyQuery query) {
        return projectApiKeyRepository.findByApiKey(query.apiKey());
    }

    @Override
    public List<ProjectApiKey> handle(GetProjectApiKeysQuery query) {
        return projectApiKeyRepository.findAllByProjectId(query.projectId());
    }
}
