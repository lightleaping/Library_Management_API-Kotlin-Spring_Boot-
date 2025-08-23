package com.example.collectionspractice.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class MessageConfig {

    @Bean
    fun messageSource(): MessageSource =
        ReloadableResourceBundleMessageSource().apply {
            setBasenames("classpath:messages")
            setDefaultEncoding("UTF-8")
            setCacheSeconds(10)
            setFallbackToSystemLocale(false)
        }

    @Bean
    fun getValidator(messageSource: MessageSource): LocalValidatorFactoryBean =
        LocalValidatorFactoryBean().apply {
            setValidationMessageSource(messageSource)
        }
}
