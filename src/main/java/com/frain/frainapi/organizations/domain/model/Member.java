package com.frain.frainapi.organizations.domain.model;

import com.frain.frainapi.organizations.domain.exceptions.OrganizationNameRequiredException;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.AuditableEntity;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member extends AuditableEntity<Member> {
    @EmbeddedId
    private MemberId id; // This id is used within the organization
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId; // This id belongs to the user and identifies the user throughout  platform

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "organization_id"))
    private OrganizationId organizationId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private MemberName name;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public Member(MemberId id, OrganizationId organizationId, UserId userId, MemberName name, MemberRole role) {
        if (organizationId == null) {
            throw new OrganizationNameRequiredException();
        }
        this.id = id;
        this.organizationId = organizationId;
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public boolean isAdminRole() {
        return this.role == MemberRole.ADMIN;
    }

    public boolean isContributor() {
        return this.role == MemberRole.CONTRIBUTOR;
    }

    public boolean isOwner() {
        return this.role == MemberRole.OWNER;
    }

    public boolean canInvitePeople() {
        if (isAdminRole() || isOwner()) {
            return true;
        }

        throw new InsufficientPermissionsException("Only owners and admins can invite people");
    }

    public void canPromoteMembers() {
        if (isOwner()) {
            return;
        }

        throw new InsufficientPermissionsException("Only owners can promote members");
    }

    public void updateMember(MemberName name, MemberRole role) {
        if (name != null) {
            this.name = name;
        }

        if (role != null) {
            this.role = role;
        }
    }

    public boolean canCreateProjects() {
        if (isAdminRole() || isOwner()) {
            return true;
        }

        throw new InsufficientPermissionsException("Only owners and admins can create projects");
    }

    public boolean canUpdateProjects() {
        if (isAdminRole() || isOwner()) {
            return true;
        }

        throw new InsufficientPermissionsException("Only owners and admins can update projects");
    }
}
