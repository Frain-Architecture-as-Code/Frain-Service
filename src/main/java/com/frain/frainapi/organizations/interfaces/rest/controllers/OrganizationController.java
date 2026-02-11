package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.exceptions.OrganizationNotFoundException;
import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.domain.services.OrganizationCommandService;
import com.frain.frainapi.organizations.domain.services.OrganizationQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.MemberQueryAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.OrganizationAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.OrganizationCommandAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.OrganizationQueryAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.CreateOrganizationRequest;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.UpdateOrganizationRequest;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.OrganizationResponse;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations")
@Tag(name = "Organization")
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


    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(@RequestBody CreateOrganizationRequest request) {
        var currentUser = userContext.getCurrentUser();

        var command = OrganizationCommandAssembler.toCreateOrganizationCommandFromRequest(request, currentUser);

        var organizationId = organizationCommandService.handle(command);

        var organizationResult = organizationQueryService.handle(new GetOrganizationByIdQuery(organizationId));

        if (organizationResult.isEmpty()) {
            throw new OrganizationNotFoundException(organizationId.toString());
        }
        var organization = organizationResult.get();
        var organizationResponse = OrganizationAssembler.toResponseFromEntity(organization);
        return ResponseEntity.ok(organizationResponse);
    }


    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable String organizationId) {
        getCurrentMemberByUserIdAndOrganizationId(organizationId);

        var organizationResult = organizationQueryService.handle(OrganizationQueryAssembler.toGetOrganizationByIdQuery(organizationId));

        if (organizationResult.isEmpty()) {
            throw new OrganizationNotFoundException(organizationId);
        }

        var organization = organizationResult.get();

        var organizationResponse = OrganizationAssembler.toResponseFromEntity(organization);

        return ResponseEntity.ok(organizationResponse);
    }

    @PatchMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> updateOrganizationById(@PathVariable String organizationId, @RequestBody UpdateOrganizationRequest request) {

        var requestingMember = getCurrentMemberByUserIdAndOrganizationId(organizationId);

        var command = OrganizationCommandAssembler.toUpdateOrganizationCommandFromRequest(organizationId, request, requestingMember);
        organizationCommandService.handle(command);

        var organizationResult = organizationQueryService.handle(OrganizationQueryAssembler.toGetOrganizationByIdQuery(organizationId));

        if (organizationResult.isEmpty()) {
            throw new OrganizationNotFoundException(organizationId);
        }

        var organization = organizationResult.get();
        var organizationResponse = OrganizationAssembler.toResponseFromEntity(organization);
        return ResponseEntity.ok(organizationResponse);
    }

    private Member getCurrentMemberByUserIdAndOrganizationId(String organizationId) {
        var currentUserId = userContext.getCurrentUserId();
        var result = memberQueryService.handle(MemberQueryAssembler.toGetMemberByUserIdAndOrganizationIdQueryFromString(currentUserId, organizationId));

        if (result.isEmpty()) {
            throw new RuntimeException("Current user is not a member of the organization");
        }

        return result.get();
    }
}
