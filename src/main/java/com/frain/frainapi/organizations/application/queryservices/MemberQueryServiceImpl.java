package com.frain.frainapi.organizations.application.queryservices;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMembersByOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.infrastructure.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;

    public MemberQueryServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> handle(GetMemberByIdQuery query) {
        return memberRepository.findById(query.memberId());
    }

    @Override
    public List<Member> handle(GetMembersByOrganizationIdQuery query) {
        return memberRepository.findAllByOrganizationId(query.organizationId());
    }

    @Override
    public Optional<Member> handle(GetMemberByUserIdAndOrganizationIdQuery query) {
        return memberRepository.findByUserIdAndOrganizationId(query.userId(), query.organizationId());
    }
}
