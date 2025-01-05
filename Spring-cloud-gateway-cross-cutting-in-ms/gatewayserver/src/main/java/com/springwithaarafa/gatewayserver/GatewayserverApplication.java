package com.springwithaarafa.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

    @Bean
	public RouteLocator customRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return  routeLocatorBuilder.routes()
				.route(p -> p
						.path("/demo/accounts/**") // Using this path predicat to validate if the given request is matching with this path
						.filters(f -> f.rewritePath("demo/accounts/(?<segment>.*)","/${segment}")
						    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/demo/loans/**")
						// Original path: /demo/accounts/some-path -> Rewritten to: /some-path
						.filters(f -> f.rewritePath("demo/loans/(?<segment>.*)","/${segment}")
						// Routes the requests to microservices identified by logical names defined in the discovery server (Eureka):
						      .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/demo/cards/**")
						.filters(f -> f.rewritePath("demo/cards/(?<segment>.*)","/${segment}")
						     .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}
}
