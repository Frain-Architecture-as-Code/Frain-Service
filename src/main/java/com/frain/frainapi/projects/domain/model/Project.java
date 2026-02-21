package com.frain.frainapi.projects.domain.model;

import com.frain.frainapi.projects.domain.model.valueobjects.*;
import com.frain.frainapi.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Updates the entire C4Model.
     * This is used when the SDK sends a complete new model.
     */
    public void updateC4Model(C4Model c4Model) {
        this.c4Model = c4Model;
    }

    /**
     * Gets a view by its ID.
     */
    public Optional<FrainViewJSON> getViewById(String viewId) {
        if (c4Model == null || c4Model.views() == null) {
            return Optional.empty();
        }
        return c4Model.views().stream()
                .filter(v -> v.id().equals(viewId))
                .findFirst();
    }

    /**
     * Updates a node's position within a view.
     */
    public void updateNodePosition(String viewId, String nodeId, int x, int y) {
        if (c4Model == null || c4Model.views() == null) {
            throw new IllegalStateException("C4Model or views not initialized");
        }

        List<FrainViewJSON> updatedViews = c4Model.views().stream()
                .map(view -> {
                    if (!view.id().equals(viewId)) {
                        return view;
                    }
                    
                    List<FrainNodeJSON> updatedNodes = updateNodeInList(view.nodes(), nodeId, x, y);
                    List<FrainNodeJSON> updatedExternalNodes = updateNodeInList(view.externalNodes(), nodeId, x, y);
                    
                    return new FrainViewJSON(
                            view.id(),
                            view.type(),
                            view.container(),
                            view.name(),
                            updatedNodes,
                            updatedExternalNodes,
                            view.relations()
                    );
                })
                .toList();

        this.c4Model = new C4Model(
                c4Model.title(),
                c4Model.description(),
                Instant.now(),
                updatedViews
        );
    }

    private List<FrainNodeJSON> updateNodeInList(List<FrainNodeJSON> nodes, String nodeId, int x, int y) {
        if (nodes == null) {
            return null;
        }
        return nodes.stream()
                .map(node -> {
                    if (node.id().equals(nodeId)) {
                        return new FrainNodeJSON(
                                node.id(),
                                node.type(),
                                node.name(),
                                node.description(),
                                node.technology(),
                                node.viewId(),
                                x,
                                y
                        );
                    }
                    return node;
                })
                .toList();
    }

    /**
     * Gets view summaries (basic info without nodes and relations).
     */
    public List<ViewSummary> getViewSummaries() {
        if (c4Model == null || c4Model.views() == null) {
            return new ArrayList<>();
        }
        return c4Model.views().stream()
                .map(view -> new ViewSummary(
                        view.id(),
                        view.type(),
                        view.name(),
                        view.container()
                ))
                .toList();
    }

    /**
     * Record for view summary data.
     */
    public record ViewSummary(
            String id,
            ViewType type,
            String name,
            ContainerInfo container
    ) {}
}
