package az.atl.msmessaging.config.security;

import az.atl.msmessaging.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService service;

    public JwtFilter(UserDetailsService userDetailsService, JwtService service) {
        this.userDetailsService = userDetailsService;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = headerAuthorization.substring(7);
        String username = service.getUsernameFromJwt(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (service.isValid(jwt, userDetails)) {
                if (service.checkIssuer(jwt)) {
                    UsernamePasswordAuthenticationToken authenticationObject = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    WebAuthenticationDetails authDetails = new WebAuthenticationDetails(request);
                    authenticationObject.setDetails(authDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationObject);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
