package app.tgid.doc;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transaction API")
                        .description("API para chamada de Serviços de Transação, criação de empresa, cliente")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Lucas Silva Rocha")
                                .url("http://localhost")
                                .email("lucassrocha.dev@gmail.com"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do Projeto")
                        .url("http://localhost:8080/swagger-ui.html"));
    }
}

