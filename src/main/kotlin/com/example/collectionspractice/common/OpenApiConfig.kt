package com.example.collectionspractice.common

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("CollectionsPractice API")
                    .description("Book API")
                    .version("v1")
                    .contact(Contact().name("Team").email("team@example.com"))
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Project README")
                    .url("https://example.com")
            )
}