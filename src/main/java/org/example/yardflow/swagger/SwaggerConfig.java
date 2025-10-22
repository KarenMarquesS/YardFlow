package org.example.yardflow.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI configurarSwagger() {
        return new OpenAPI().info(new Info()
                .title("projeto de Gestão Pátio de Motos").description("Esse projeto " +
                        "oferece uma implementação que possibilida a gestão de motos no pátio")
                .summary("Sumário: descrição mais detalhada essa aplicação é parte da proposta de solução para" +
                        "manter e organizar o pátio de motos")
                .version("v1.0.0")
                .license(new License().url("www.DevForge.com.br").name("Licença - ·DEV·FORGE· - v1.1.2")));
    }
}
