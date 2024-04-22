package xyz.toway.emarket.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain) throws ServletException, IOException {
        Objects.requireNonNull(request);
        Objects.requireNonNull(filterChain);

        String token = securityService.getTokenFromRequest(request);
        if (StringUtils.hasText(token) && SecurityContextHolder.getContext().getAuthentication() == null && securityService.validateExpirationToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(securityService.getAuthentication(token));
        }
        filterChain.doFilter(request, response);
    }
}
