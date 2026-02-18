package com.frain.frainapi.organizations.domain.model.events;

import com.frain.frainapi.organizations.domain.model.Invitation;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserName;

public record InvitationAcceptedEvent(Invitation invitation, UserId performBy, UserName performByName, String newMemberPicture) {
}
