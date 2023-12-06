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

	private static final String URI_POST_SERVICE = "http://localhost:8082";

	private static final String PATH_POSTS = "/api/posts/**";

	private static final String USER_SERVICE = "http://localhost:8081";

	private static final String PATH_USERS = "/api/users/**";
			// Here should be users/** not users** because users** isn't a valid path

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
			return builder.routes()
					// we are calling this route $ curl http://localhost:8080/get
			.route("example_working_route", p -> p
					.path("/get")
					.filters(f -> f
							.addRequestHeader("1Hello", "1World")
							.addResponseHeader("2Hello", "2World"))
					.uri("http://httpbin.org:80")
			)
					// Those routes don't work because when running with docker-compose there is a problem with the DNS
//			.route("post_service_route", r -> r
//					.path(PATH_POSTS)
//					.uri(URI_POST_SERVICE)
//			)
//			.route("user_service_route", r -> r
//					.path(PATH_USERS)
//					.uri(USER_SERVICE)
//			)
			.build();


	}
}
