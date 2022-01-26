package com.moscow.neighbours.backend.jwt.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    // MARK: - Properties

    private final Algorithm algorithm;

    private final String[] requestsWithoutAuthorization;

    // MARK: - Constructor

    public JwtAuthTokenFilter(
            Algorithm algorithm
    ) {
        this.algorithm = algorithm;
        requestsWithoutAuthorization = new String[] {
                "/refreshToken",
                "/signIn",
                "/signUp"
        };
    }

    // MARK: - Methods

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwt = getJwt(request);
            if (jwt.isPresent() && requestWithAuthorization(request)) {
                var verifier = JWT.require(algorithm).build();
                var decodedJWT = verifier.verify(jwt.get());
                var email = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority((role)));
                }
                var authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (JWTVerificationException e) {
            logger.error(String.format("JWT verification exception: %s", e.getMessage()));
            setErrorInResponse(response);
        } catch (Exception e) {
            logger.error(String.format("Can not set user auth: %s", e.getMessage()));
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts jwt token from request
     * @param request request
     * @return Optional with jwt token
     */
    public Optional<String> getJwt(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return Optional.of(authHeader.replace("Bearer ", ""));
        }
        return Optional.empty();
    }

    private boolean requestWithAuthorization(HttpServletRequest request) {
        var path = request.getServletPath();
        return Arrays.stream(requestsWithoutAuthorization).noneMatch(path::contains);
    }

    private void setErrorInResponse(HttpServletResponse response) {
        try {
            response.setStatus(UNAUTHORIZED.value());
            Map<String, String> error = new HashMap<>();
            error.put("message", "User is not authorized");
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        } catch (Exception e) {
            logger.error("Can not set error in response: " + e.getMessage());
        }
    }

}
