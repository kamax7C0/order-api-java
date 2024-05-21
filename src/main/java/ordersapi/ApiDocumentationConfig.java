package ordersapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .openapi("3.0.3")
                .info(new Info()
                        .title("Retail Orders API")
                        .description("Sample API, that highlights OpenAPI documentation generation")
                        .version("0.0.1")
                        .summary("this is a summary")
                        .termsOfService("https://tos.my-company.com")
                        .contact(new Contact()
                                .name("Max Kgn")
                                .email("max.kgn@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation")
                        .url("https://wiki.example.com/documentation"))
                .addSecurityItem(new SecurityRequirement().addList("client_id").addList("client_secret"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("client_id", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("client_id"))
                        .addSecuritySchemes("client_secret", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("client_secret")))
                .addServersItem(new Server().url("https://dev.api.example.com").description("Development server"))
                .addServersItem(new Server().url("https://staging.api.example.com").description("Staging server"))
                .addServersItem(new Server().url("https://api.example.com").description("Production server"));
    }
}