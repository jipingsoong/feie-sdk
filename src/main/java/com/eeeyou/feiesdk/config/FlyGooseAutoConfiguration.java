package com.eeeyou.feiesdk.config;

import com.eeeyou.feiesdk.client.PrintClient;
import com.eeeyou.feiesdk.client.RestTemplateHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FlyGooseConfig.class)
public class FlyGoolseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PrintClient printClient(FlyGooseConfig flyGooseConfig, RestTemplateHttpClient httpClient) {
        return new PrintClient(flyGooseConfig, httpClient);
    }


    @Bean
    @ConditionalOnMissingBean
    public RestTemplateHttpClient restTemplateHttpClient() {
        return new RestTemplateHttpClient();
    }
}
