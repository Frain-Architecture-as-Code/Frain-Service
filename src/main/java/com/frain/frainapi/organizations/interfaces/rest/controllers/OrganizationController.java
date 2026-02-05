package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.exceptions.OrganizationNotFoundException;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.domain.services.OrganizationCommandService;
import com.frain.frainapi.organizations.domain.services.OrganizationQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.OrganizationAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.OrganizationResponse;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    private final OrganizationCommandService organizationCommandService;
    private final OrganizationQueryService organizationQueryService;
    private final MemberQueryService memberQueryService;

    private final UserContext userContext;

    public OrganizationController(OrganizationCommandService organizationCommandService, OrganizationQueryService organizationQueryService, MemberQueryService memberQueryService, UserContext userContext) {
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
        this.memberQueryService = memberQueryService;
        this.userContext = userContext;
    }


    @GetMapping
    @RequestMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable OrganizationId organizationId) {
        var currentUserId = userContext.getCurrentUserId();
        var result = memberQueryService.handle(new GetMemberByUserIdAndOrganizationIdQuery(currentUserId, organizationId));

        if (result.isEmpty()) {
            throw new RuntimeException("Current user is not a member of the organization");
        }

        var organizationResult = organizationQueryService.handle(new GetOrganizationByIdQuery(organizationId));

        if (organizationResult.isEmpty()) {
            throw new OrganizationNotFoundException(organizationId);
        }

        var organization = organizationResult.get();

        var organizationResponse = OrganizationAssembler.toResponseFromEntity(organization);

        return ResponseEntity.ok(organizationResponse);
    }


}
