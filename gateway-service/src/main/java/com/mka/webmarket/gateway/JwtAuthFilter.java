package com.mka.webmarket.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {
    @Autowired
    private JwtUtils jwtUtils;

    public JwtAuthFilter() {
        super(configClass);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!isAuthMissing(request)) {
                final String token = getAuthHeader(request);
                if ()
            }
        })
    }
}
