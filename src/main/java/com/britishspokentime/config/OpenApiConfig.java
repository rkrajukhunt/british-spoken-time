package com.britishspokentime.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI (Swagger) documentation.
 * Provides API documentation accessible at /swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

  /**
   * Configures OpenAPI documentation for the British Spoken Time API.
   *
   * @return configured OpenAPI instance
   */
  @Bean
  public OpenAPI britishSpokenTimeOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("British Spoken Time API")
            .description("REST API for converting digital time format (HH:mm) to "
                + "British spoken form. For example, converts '12:00' to 'noon' "
                + "and '7:30' to 'half past seven'.")
            .version("1.0.0")
            .contact(new Contact()
                .name("Raju Khunt")
                .url("https://github.com/rkrajukhunt/british-spoken-time"))
            .license(new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT")));
  }
}
