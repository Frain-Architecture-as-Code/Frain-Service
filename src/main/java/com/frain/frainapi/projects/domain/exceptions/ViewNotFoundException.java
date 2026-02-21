package com.frain.frainapi.projects.domain.exceptions;

public class ViewNotFoundException extends RuntimeException {
    public ViewNotFoundException(String viewId) {
        super("View not found: " + viewId);
    }

    public ViewNotFoundException(String viewId, String projectId) {
        super("View '" + viewId + "' not found in project: " + projectId);
    }
}
