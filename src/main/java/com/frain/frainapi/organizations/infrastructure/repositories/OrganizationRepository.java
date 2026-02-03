package com.frain.frainapi.organizations.infrastructure.repositories;

import com.frain.frainapi.organizations.domain.model.Organization;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, OrganizationId> {
    List<Organization> findAllByVisibility(OrganizationVisibility visibility);
}
