package com.developaw.harupuppy.global.configuration.filter;

import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import com.developaw.harupuppy.global.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PERMIT_URLS =
            List.of("/", "/h2", "/auth/login/*", "/api/users/register", "/api/users/invitation/*");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return PERMIT_URLS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Authorization Header does not start with Bearer {}", request.getRequestURI());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        final String token = header.split(" ")[1].trim();
        if (jwtTokenUtils.isExpired(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Long userId = jwtTokenUtils.resolveToken(token);
        UserDetail user = userService.loadByUserId(userId);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.info("Successfully verification of token. URI: {}", request.getRequestURI());

        filterChain.doFilter(request, response);
    }
}
