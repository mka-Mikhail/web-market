package com.mka.webmarket.gateway;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (request.getHeaders().containsKey("username")) {
                return this.onError(exchange, "Invalid header username", HttpStatus.BAD_REQUEST);
            }

            if (!isAuthMissing(request)) {
                final String token = getAuthHeader(request);
                if (jwtUtils.isInvalid(token)) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
}
