package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMembersByOrganizationIdQuery;

import java.util.List;
import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> handle(GetMemberByIdQuery query);
    List<Member> handle(GetMembersByOrganizationIdQuery query);
    Optional<Member> handle(GetMemberByUserIdAndOrganizationIdQuery query);
}
