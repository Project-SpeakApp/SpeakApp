package com.speakapp.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
//@Import(AdditionalRoutes.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	private static final String URI_POST_SERVICE = "http://httpbin.org:80"; //"http://post_service:8082";

	private static final String PATH_POSTS = "/api/posts/**";

	private static final String USER_SERVICE = "http://user_service:8081";

	private static final String PATH_USERS = "/api/users/**"; // TODO: change to /api/users**

	//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				// Common approach for posts and users microservices
//
//				.route("post_service_route", r -> r
//						//.path(PATH_POSTS)
//
//						.uri(URI_POST_SERVICE)
//				)
////				.route("user_service_route", r -> r
////						.path(PATH_USERS)
////						.uri(USER_SERVICE)
////				)
//
//				.build();
//	}
	public static final String HELLO_FROM_FAKE_ACTUATOR_METRICS_GATEWAY_REQUESTS = "hello from fake /actuator/metrics/spring.cloud.gateway.requests";

	@Value("${test.uri:http://httpbin.org:80}")
	String uri;
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		//@formatter:off
	// String uri = "http://httpbin.org:80";
	// String uri = "http://localhost:9080";
	return builder.routes()
			.route(r -> r.host("**.abc.org").and().path("/anything/png")
					.filters(f ->
							f.prefixPath("/httpbin")
									.addResponseHeader("X-TestHeader", "foobar"))
					.uri(uri)
			).build();
	}
}
