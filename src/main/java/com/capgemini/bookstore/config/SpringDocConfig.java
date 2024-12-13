package com.capgemini.bookstore.config;

import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * This class sets up the required bean configuration for using a spec.yaml together
 * with springdoc-openapi.
 *
 * See: https://springdoc.org/#what-is-a-proper-way-to-set-up-swagger-ui-to-use-provided-spec-yml
 *
 * One thing they don't mention is that your spec file should be servable by Spring Boot,
 * so it needs to be in the resources/static folder.
 */
@Configuration
public class SpringDocConfig {
    @Bean
    SpringDocConfiguration springDocConfiguration(){
        return new SpringDocConfiguration();
    }

    @Bean
    SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    @Bean
    ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties){
        return new ObjectMapperProvider(springDocConfigProperties);
    }

    @Bean
    SpringDocUIConfiguration SpringDocUIConfiguration(Optional<SwaggerUiConfigProperties> optionalSwaggerUiConfigProperties){
        return new SpringDocUIConfiguration(optionalSwaggerUiConfigProperties);
    }
}
