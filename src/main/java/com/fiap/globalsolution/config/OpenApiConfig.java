package com.fiap.globalsolution.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Global Solution API")
                        .version("1.0")
                        .description("API RESTful para gerenciamento de funcionários, empresas, cursos e times")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .addServersItem(new Server().url("http://localhost:8083").description("Servidor local"));
    }
}