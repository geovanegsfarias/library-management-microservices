package com.github.geovanegsfarias.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Service")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Geovane")
                                .email("geovane.gsfarias@gmail.com"))
                )
                .addTagsItem(new Tag().name("Book"));
    }

}
