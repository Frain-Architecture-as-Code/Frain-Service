package com.frain.frainapi.shared.infrastructure.security;

import com.frain.frainapi.projects.domain.model.ProjectApiKey;
import com.frain.frainapi.projects.domain.model.commands.RecordApiKeyUsageCommand;
import com.frain.frainapi.projects.domain.model.queries.GetProjectApiKeyByKeyQuery;
import com.frain.frainapi.projects.domain.model.valueobjects.ApiKey;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.domain.services.ProjectApiKeyCommandService;
import com.frain.frainapi.projects.domain.services.ProjectApiKeyQueryService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "Frain-Api-Key";
    private static final Pattern SDK_PATH_PATTERN = Pattern.compile("^/api/v1/c4models/projects/([^/]+)/sdk$");

    private final ProjectApiKeyQueryService projectApiKeyQueryService;
    private final ProjectApiKeyCommandService projectApiKeyCommandService;

    public ApiKeyAuthenticationFilter(
            ProjectApiKeyQueryService projectApiKeyQueryService,
            ProjectApiKeyCommandService projectApiKeyCommandService) {
        this.projectApiKeyQueryService = projectApiKeyQueryService;
        this.projectApiKeyCommandService = projectApiKeyCommandService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.matches("^/api/v1/c4models/projects/[^/]+/sdk$");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        String apiKeyHeader = request.getHeader(API_KEY_HEADER);

        if (apiKeyHeader == null || apiKeyHeader.isBlank()) {
            log.debug("No API key header found for SDK endpoint: {}", path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Missing Frain-Api-Key header\"}");
            return;
        }

        // Validate the API key
        ApiKey apiKey = new ApiKey(apiKeyHeader);
        Optional<ProjectApiKey> projectApiKeyOpt = projectApiKeyQueryService.handle(
                new GetProjectApiKeyByKeyQuery(apiKey)
        );

        if (projectApiKeyOpt.isEmpty()) {
            log.warn("Invalid API key attempted: {}", apiKeyHeader.substring(0, Math.min(12, apiKeyHeader.length())) + "...");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid API key\"}");
            return;
        }

        ProjectApiKey projectApiKey = projectApiKeyOpt.get();

        // Extract projectId from path and validate it matches the API key's project
        Matcher matcher = SDK_PATH_PATTERN.matcher(path);
        if (matcher.matches()) {
            String pathProjectId = matcher.group(1);
            ProjectId apiKeyProjectId = projectApiKey.getProjectId();

            if (!pathProjectId.equals(apiKeyProjectId.toString())) {
                log.warn("API key project mismatch. Path: {}, API Key project: {}",
                        pathProjectId, apiKeyProjectId);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"API key not authorized for this project\"}");
                return;
            }
        }

        // Create authenticated token and set in security context
        ApiKeyAuthenticationToken authentication = new ApiKeyAuthenticationToken(
                apiKeyHeader,
                projectApiKey.getProjectId(),
                projectApiKey.getMemberId(),
                projectApiKey.getId(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_KEY"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Record API key usage (fire and forget - don't block the request)
        try {
            projectApiKeyCommandService.handle(new RecordApiKeyUsageCommand(projectApiKey.getId()));
        } catch (Exception e) {
            // Log but don't fail the request if usage recording fails
            log.warn("Failed to record API key usage for key {}: {}", projectApiKey.getId(), e.getMessage());
        }

        log.debug("API key authentication successful for project: {}", projectApiKey.getProjectId());

        filterChain.doFilter(request, response);
    }
}