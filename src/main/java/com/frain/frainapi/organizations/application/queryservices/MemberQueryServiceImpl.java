package com.frain.frainapi.organizations.application.queryservices;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMembersByOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;

import java.util.List;
import java.util.Optional;

public class MemberQueryServiceImpl implements MemberQueryService {

    @Override
    public Optional<Member> handle(GetMemberByIdQuery query) {
        return Optional.empty();
    }

    @Override
    public List<Member> handle(GetMembersByOrganizationIdQuery query) {
        return List.of();
    }

    @Override
    public Optional<Member> handle(GetMemberByUserIdAndOrganizationIdQuery query) {
        return Optional.empty();
    }
}
