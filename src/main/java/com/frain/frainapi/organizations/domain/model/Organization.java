package com.frain.frainapi.organizations.domain.model;

import com.frain.frainapi.organizations.domain.exceptions.OrganizationOwnerAlreadyExistsException;
import com.frain.frainapi.organizations.domain.model.valueobjects.*;
import com.frain.frainapi.shared.domain.AuditableEntity;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Organization extends AuditableEntity<Organization> {
    @EmbeddedId
    private OrganizationId id;

    @Embedded
    private MemberId ownerId;

    @Embedded
    @Setter
    private OrganizationName name;

    @Enumerated(EnumType.STRING)
    @Setter
    private OrganizationVisibility visibility;

    public Organization(OrganizationId id, OrganizationName name, MemberId ownerId, OrganizationVisibility visibility) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.visibility = visibility;
    }

    public void setOwner(MemberId ownerId) {
        if (ownerId == null) {
            throw new OrganizationOwnerAlreadyExistsException(this.getId().toString());
        }
        this.ownerId = ownerId;
    }

    public void transferOwnership(MemberId newOwnerId) {
        this.ownerId = newOwnerId;
    }

    public Member enrollMember(MemberId memberId, UserId userId, MemberName name, MemberRole role) {
        return new Member(memberId, this.id, userId, name, role);
    }
}
