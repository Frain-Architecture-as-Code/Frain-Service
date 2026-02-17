package com.frain.frainapi.organizations.domain.model;

import com.frain.frainapi.organizations.domain.exceptions.OrganizationOwnerAlreadyExistsException;
import com.frain.frainapi.organizations.domain.model.valueobjects.*;
import com.frain.frainapi.shared.domain.model.AuditableEntity;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Organization extends AuditableEntity<Organization> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private OrganizationId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "owner_member_id"))
    private MemberId ownerMemberId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private OrganizationName name;

    @Enumerated(EnumType.STRING)
    private OrganizationVisibility visibility;

    public Organization(OrganizationId id, OrganizationName name, MemberId ownerMemberId, OrganizationVisibility visibility) {
        this.id = id;
        this.name = name;
        this.ownerMemberId = ownerMemberId;
        this.visibility = visibility;
    }

    public void setOwner(MemberId ownerId) {
        if (ownerId == null) {
            throw new OrganizationOwnerAlreadyExistsException(this.getId().toString());
        }
        this.ownerMemberId = ownerId;
    }

    public void updateDetails(OrganizationName newName, OrganizationVisibility newVisibility) {
        if (newName != null) {
             this.name = newName;
        }
        if (visibility != null) {
            this.visibility = newVisibility;
        }
    }

    public void transferOwnership(MemberId newOwnerId) {
        this.ownerMemberId = newOwnerId;
    }

    public Member enrollMember(MemberId memberId, UserId userId, MemberName name, MemberRole role) {
        return new Member(memberId, this.id, userId, name, role);
    }
}
