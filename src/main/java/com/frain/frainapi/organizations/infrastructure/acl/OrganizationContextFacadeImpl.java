package com.frain.frainapi.organizations.infrastructure.acl;

import com.frain.frainapi.organizations.domain.exceptions.MemberNotFoundException;
import com.frain.frainapi.organizations.domain.exceptions.UserNotFoundInOrganizationException;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.infrastructure.repositories.MemberRepository;
import com.frain.frainapi.organizations.interfaces.acl.OrganizationContextFacade;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationContextFacadeImpl implements OrganizationContextFacade {

    private final MemberRepository memberRepository;

    public OrganizationContextFacadeImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public boolean isMemberOfOrganization(String organizationId, String userId) {
        var orgId = OrganizationId.fromString(organizationId);
        var usrId = UserId.fromString(userId);

        return memberRepository.existsByUserIdAndOrganizationId(usrId, orgId);
    }

    @Override
    public String getMemberIdForUserInOrganization(String organizationId, String userId) {
        var orgId = OrganizationId.fromString(organizationId);
        var usrId = UserId.fromString(userId);

        var result = memberRepository.findByUserIdAndOrganizationId(usrId, orgId);

        if (result.isPresent()) {
            return result.get().getId().toString();
        }

        throw new UserNotFoundInOrganizationException(usrId, orgId);
    }

    @Override
    public boolean canCreateProjects(String organizationId, String userId) {
        var orgId = OrganizationId.fromString(organizationId);
        var usrId = UserId.fromString(userId);

        var result = memberRepository.findByUserIdAndOrganizationId(usrId, orgId);

        if (!result.isPresent()) {
            throw new UserNotFoundInOrganizationException(usrId, orgId);
        }

        var member = result.get();

        return member.canCreateProjects();
    }

    @Override
    public boolean canUpdateProject(String organizationId, String userId) {
        var orgId = OrganizationId.fromString(organizationId);
        var usrId = UserId.fromString(userId);

        var result = memberRepository.findByUserIdAndOrganizationId(usrId, orgId);

        if (!result.isPresent()) {
            throw new UserNotFoundInOrganizationException(usrId, orgId);
        }

        var member = result.get();

        return member.canUpdateProjects();
    }

    @Override
    public String getMemberRole(String organizationId, String memberId) {
        var orgId = OrganizationId.fromString(organizationId);
        var memId = MemberId.fromString(memberId);

        var result = memberRepository.findById(memId);

        if (result.isEmpty()) {
            throw new MemberNotFoundException(memId);
        }

        var member = result.get();

        if (!member.getOrganizationId().equals(orgId)) {
            throw new MemberNotFoundException(memId);
        }

        return member.getRole().name();
    }

    @Override
    public String getOrganizationIdByProjectId(String projectId) {
        // This method needs to be implemented by querying the project repository
        // For now, we'll throw an exception as this requires access to the projects bounded context
        // This will be called from a service that has access to both contexts
        throw new UnsupportedOperationException("This method should be implemented through project repository access");
    }


}
