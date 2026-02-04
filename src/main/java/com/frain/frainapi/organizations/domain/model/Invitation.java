package com.frain.frainapi.organizations.domain.model;

import com.frain.frainapi.organizations.domain.model.valueobjects.*;
import com.frain.frainapi.shared.domain.model.AuditableEntity;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Invitation extends AuditableEntity<Invitation> {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private InvitationId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "target_email"))
    private EmailAddress targetEmail;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "organization_id"))
    private OrganizationId organizationId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "inviter_id"))
    private MemberId inviterId;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    public Invitation(InvitationId id, OrganizationId organizationId, MemberId inviterId, EmailAddress targetEmail, MemberRole role) {
        this.id = id;
        this.organizationId = organizationId;
        this.targetEmail = targetEmail;
        this.role = role;
        this.status = InvitationStatus.PENDING;
        this.inviterId = inviterId;
    }

    public void accept() {
        this.status = InvitationStatus.ACCEPTED;
    }
    public void decline() {
        this.status = InvitationStatus.DECLINED;
    }
    public void updateStatus(InvitationStatus status) {
        if (this.status == InvitationStatus.PENDING) {
            this.status = status;
        }
    }
}
