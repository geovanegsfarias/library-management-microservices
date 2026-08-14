package com.geovanegsfarias.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
@EnableConfigurationProperties(GatewayProperties.class)
public class GatewayConfig {
    private final GatewayProperties gatewayProperties;

    public GatewayConfig(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

    @Bean
    public RouterFunction<ServerResponse> bookServiceRoute() {
        return GatewayRouterFunctions.route("book_service")
                .route(RequestPredicates.path("/v1/books/**"),
                        HandlerFunctions.http()).before(BeforeFilterFunctions.uri(gatewayProperties.bookServiceUri()))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> loanServiceRoute() {
        return GatewayRouterFunctions.route("loan_service")
                .route(RequestPredicates.path("/v1/loans/**")
                                .or(RequestPredicates.path("/v1/auth/**")),
                        HandlerFunctions.http()).before(BeforeFilterFunctions.uri(gatewayProperties.loanServiceUri()))
                .build();
    }
}
