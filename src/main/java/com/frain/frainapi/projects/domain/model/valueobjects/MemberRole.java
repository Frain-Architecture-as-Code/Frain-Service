package com.frain.frainapi.projects.domain.model.valueobjects;

/**
 * Represents a member's role within the projects context.
 * This is a local representation that mirrors the organization's role hierarchy.
 */
public enum MemberRole {
    OWNER(3),
    ADMIN(2),
    CONTRIBUTOR(1);

    private final int hierarchyLevel;

    MemberRole(int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public boolean canManageRole(MemberRole targetRole) {
        return this.hierarchyLevel > targetRole.hierarchyLevel;
    }

    public boolean isOwner() {
        return this == OWNER;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isContributor() {
        return this == CONTRIBUTOR;
    }

    public boolean canCreateApiKeys() {
        return this == OWNER || this == ADMIN;
    }

    public static MemberRole fromString(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or blank");
        }
        return switch (role.toUpperCase()) {
            case "OWNER" -> OWNER;
            case "ADMIN" -> ADMIN;
            case "CONTRIBUTOR" -> CONTRIBUTOR;
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }
}
