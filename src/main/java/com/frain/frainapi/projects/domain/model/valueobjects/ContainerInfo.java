package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.validation.constraints.NotBlank;

public record ContainerInfo(
        @NotBlank(message = "Container name is required")
        String name,

        @NotBlank(message = "Container description is required")
        String description,

        @NotBlank(message = "Container technology is required")
        String technology
) {}
