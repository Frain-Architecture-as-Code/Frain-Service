package com.frain.frainapi.projects.domain.model;

import com.frain.frainapi.projects.domain.model.valueobjects.C4Model;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectVisibility;
import com.frain.frainapi.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor
@Getter
public class Project extends AuditableEntity<Project> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "project_id"))
    private ProjectId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "organization_id"))
    private OrganizationId organizationId;

    @Enumerated(EnumType.STRING)
    private ProjectVisibility visibility;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private C4Model c4Model;

    public Project(ProjectId id, OrganizationId organizationId, ProjectVisibility visibility) {
        this.id = id;
        this.organizationId = organizationId;
        this.visibility = visibility;
        this.c4Model = null;
    }


    public void updateVisibility(ProjectVisibility visibility) {
        this.visibility = visibility;
    }

}
