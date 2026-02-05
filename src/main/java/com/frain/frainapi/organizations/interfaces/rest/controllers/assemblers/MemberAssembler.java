package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.MemberResponse;

import java.util.List;

public class MemberAssembler {

    public static MemberResponse toResponseFromEntity(Member member) {
        return new MemberResponse(
                member.getId().toString(),
                member.getUserId().toString(),
                member.getOrganizationId().toString(),
                member.getName().toString(),
                member.getRole().toString(),
                member.getCreatedAt().toString(),
                member.getUpdatedAt().toString()
        );
    }

    public static List<MemberResponse> toResponsesFromEntities(List<Member> members) {
        return members.stream()
                .map(MemberAssembler::toResponseFromEntity)
                .toList();
    }
}
