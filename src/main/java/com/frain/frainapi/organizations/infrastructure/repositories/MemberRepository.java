package com.frain.frainapi.organizations.infrastructure.repositories;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {
    Optional<Member> findByUserIdAndOrganizationId(UserId userId, OrganizationId organizationId);

    List<Member> findAllByOrganizationId(OrganizationId organizationId);

    boolean existsByUserIdAndOrganizationId(UserId userId, OrganizationId organizationId);

    void deleteAllByOrganizationId(OrganizationId organizationId);

    boolean existsMemberByName(MemberName name);
}
