package com.frain.frainapi.organizations.infrastructure.repositories;

import com.frain.frainapi.organizations.domain.model.Organization;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationVisibility;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, OrganizationId> {
    List<Organization> findAllByVisibility(OrganizationVisibility visibility);

    @Query("""
                SELECT o 
                FROM Organization o 
                WHERE o.id IN (
                    SELECT m.organizationId 
                    FROM Member m 
                    WHERE m.userId = :userId
                )
            """)
    List<Organization> findAllByMemberUserId(@Param("userId") UserId userId);
}
