package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;

public record UpdateMemberCommand(MemberName name, MemberRole role) {
}
