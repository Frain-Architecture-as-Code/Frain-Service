package com.frain.frainapi.projects.domain.exceptions;

import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;

public class ApiKeyAlreadyExistsException extends RuntimeException {
    public ApiKeyAlreadyExistsException(ProjectId projectId, MemberId memberId) {
        super("API key already exists for member " + memberId.toString() + " in project " + projectId.toString());
    }
}
