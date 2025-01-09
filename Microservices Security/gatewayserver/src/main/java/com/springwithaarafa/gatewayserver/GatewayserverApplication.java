package com.springwithaarafa.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;


@SpringBootApplication
public class GatewayserverApplication {

	private boolean circuitBreakerEnabled = true;

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

    @Bean
	public RouteLocator customRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return  routeLocatorBuilder.routes()
				.route(p -> p
						.path("/demo/accounts/**") // Using this path predicat to validate if the given request is matching with this path
						.filters(f -> {
							f.rewritePath("/demo/accounts/(?<segment>.*)", "/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now()
											.toString());
							if (circuitBreakerEnabled) {
								f.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
										.setFallbackUri("forward:/contactSupport"));
							}
							return f;
						})
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/demo/loans/**")
						// Original path: /demo/accounts/some-path -> Rewritten to: /some-path
						.filters(f -> f.rewritePath("demo/loans/(?<segment>.*)","/${segment}")
						// Routes the requests to microservices identified by logical names defined in the discovery server (Eureka):
						      .addResponseHeader("X-Response-Timeee", LocalDateTime.now().toString())
								.retry(retryconfig -> retryconfig.setRetries(3).setMethods(HttpMethod.GET).setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2 ,true )))
								.uri("lb://LOANS"))
										// Retry only for HTTP GET methods
										// Set a maximum of 3 retries
										// Configure backoff with:
										// - Initial delay: 100ms
										// - Max delay: 1000ms
										// - Exponential multiplier: 2
										// - Enable jitter for randomized backoff // The retry intervals are randomized within a range (e.g., 90–110ms for the first retry),
				.route(p -> p
						.path("/demo/cards/**")
						.filters(f -> f.rewritePath("demo/cards/(?<segment>.*)","/${segment}")
						     .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}
}
