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

    @Bean
    public RouterFunction<ServerResponse> bookServiceDocsRoute() {
        return GatewayRouterFunctions.route("book_service_docs")
                .route(RequestPredicates.path("/v3/api-docs/book-service"),
                        HandlerFunctions.http())
                .before(BeforeFilterFunctions.rewritePath("/v3/api-docs/book-service", "/v3/api-docs"))
                .before(BeforeFilterFunctions.uri(gatewayProperties.bookServiceUri()))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> loanServiceDocsRoute() {
        return GatewayRouterFunctions.route("loan_service_docs")
                .route(RequestPredicates.path("/v3/api-docs/loan-service"),
                        HandlerFunctions.http())
                .before(BeforeFilterFunctions.rewritePath("/v3/api-docs/loan-service", "/v3/api-docs"))
                .before(BeforeFilterFunctions.uri(gatewayProperties.loanServiceUri()))
                .build();
    }
}
