package com.eeeyou.feiesdk.config;

import com.eeeyou.feiesdk.client.FlyGoosePrintClient;
import com.eeeyou.feiesdk.client.RestTemplateHttpClient;
import com.eeeyou.feiesdk.service.PrintCallbackDispatcher;
import com.eeeyou.feiesdk.service.PrintCallbackHandler;
import com.eeeyou.feiesdk.service.ScanCallbackDispatcher;
import com.eeeyou.feiesdk.service.ScanCallbackHandler;
import com.eeeyou.feiesdk.utils.ConfigUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * FlyGooseAutoConfiguration
 * <p>
 * 飞鹅打印机自动配置
 *
 * @author : SongJiping
 * @since : 2025/11/4 上午11:59
 */
@Configuration
@EnableConfigurationProperties(FlyGooseConfig.class)
public class FlyGooseAutoConfiguration {

    /**
     * 打印客户端
     *
     * @param httpClient http客户端
     * @return 打印客户端
     */
    @Bean
    @ConditionalOnMissingBean
    public FlyGoosePrintClient printClient(RestTemplateHttpClient httpClient) {
        return new FlyGoosePrintClient(httpClient);
    }

    /**
     * http客户端
     *
     * @param flyGooseConfig 配置
     * @return http客户端
     */
    @Bean
    @ConditionalOnMissingBean
    public RestTemplateHttpClient restTemplateHttpClient(FlyGooseConfig flyGooseConfig) {
        return new RestTemplateHttpClient(flyGooseConfig);
    }

    /**
     * 飞鹅打印配置
     *
     * @return 配置
     */
    @Bean
    @ConditionalOnMissingBean
    public FlyGooseConfig flyGooseConfig() {
        return new FlyGooseConfig();
    }

    /**
     * 配置工具
     *
     * @param flyGooseConfig 配置
     * @return 配置工具
     */
    @Bean
    @ConditionalOnMissingBean
    public ConfigUtils configUtils(FlyGooseConfig flyGooseConfig) {
        return new ConfigUtils(flyGooseConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public PrintCallbackDispatcher printCallbackDispatcher(List<PrintCallbackHandler> handlers,
                                                           FlyGooseConfig flyGooseConfig) {
        return new PrintCallbackDispatcher(handlers, flyGooseConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public ScanCallbackDispatcher scanCallbackDispatcher(List<ScanCallbackHandler> handlers,
                                                         FlyGooseConfig flyGooseConfig) {
        return new ScanCallbackDispatcher(handlers, flyGooseConfig);
    }
}
