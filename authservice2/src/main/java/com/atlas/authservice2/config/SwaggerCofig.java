package com.atlas.authservice2.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
@Configuration
@OpenAPIDefinition
public class SwaggerCofig {

	@Value("$server.port")
	private String port;

	@Bean
	OpenAPI apiInfo() {
		final String securitySchemeName = "bearerAuth";
		List<Server> servers = new ArrayList<>();
		servers.add(new Server().url("http://localhost:" + port).description("localhostServerUrl"));
		return new OpenAPI().servers(servers).addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
								.in(SecurityScheme.In.HEADER).name("Bearer Authentication")))
				.info(new Info().title("AuthenticationRestApi")
						.description("RestApi for Managing Microservice app and user accounts").version("v0.0.1"));
	}
}
