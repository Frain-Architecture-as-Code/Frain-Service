package com.frain.frainapi.projects.domain.model;

import com.frain.frainapi.projects.domain.model.valueobjects.ApiKey;
import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ProjectApiKey extends AuditableEntity<ProjectApiKey> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "project_api_key_id"))
    private ProjectApiKeyId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "project_id"))
    private ProjectId projectId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "member_id"))
    private MemberId memberId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "api_key"))
    private ApiKey apiKey;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;

    public ProjectApiKey(ProjectApiKeyId id, ProjectId projectId, MemberId memberId, ApiKey apiKey) {
        this.id = id;
        this.projectId = projectId;
        this.memberId = memberId;
        this.apiKey = apiKey;
    }

    public void recordUsage() {
        this.lastUsedAt = LocalDateTime.now();
    }
}
