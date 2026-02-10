package com.frain.frainapi.projects.interfaces.rest.controllers;

import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/projects")
public class ProjectController {


    @GetMapping
    public EntityResponse<?> createProject(@PathVariable OrganizationId organizationId) {

    }
}
