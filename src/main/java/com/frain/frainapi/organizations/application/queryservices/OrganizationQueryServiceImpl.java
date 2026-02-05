package com.frain.frainapi.organizations.application.queryservices;

import com.frain.frainapi.organizations.domain.model.Organization;
import com.frain.frainapi.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetUserOrganizationsQuery;
import com.frain.frainapi.organizations.domain.services.OrganizationQueryService;
import com.frain.frainapi.organizations.infrastructure.repositories.MemberRepository;
import com.frain.frainapi.organizations.infrastructure.repositories.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrganizationQueryServiceImpl implements OrganizationQueryService {
    private final OrganizationRepository organizationRepository;

    public OrganizationQueryServiceImpl(OrganizationRepository organizationRepository, MemberRepository memberRepository) {
        this.organizationRepository = organizationRepository;
    }


    @Override
    public Optional<Organization> handle(GetOrganizationByIdQuery query) {
        return organizationRepository.findById(query.organizationId());
    }

    @Override
    public List<Organization> handle(GetUserOrganizationsQuery query) {
        var result = organizationRepository.findAllByMemberUserId(query.userId());

        log.info("Found {} organizations for user {}", result.size(), query.userId());

        return result;
    }
}
