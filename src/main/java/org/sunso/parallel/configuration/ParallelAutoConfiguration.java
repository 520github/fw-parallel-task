package org.sunso.parallel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sunso.parallel.helper.ApplicationContextHelper;
import org.sunso.parallel.task.chain.ApplicationContextParallelTaskChain;

/**
 * springBoot自动配置类
 */
@Configuration
public class ParallelAutoConfiguration {

    @Bean
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }

    @Bean
    public ApplicationContextParallelTaskChain applicationContextParallelTaskChain() {
        return new ApplicationContextParallelTaskChain();
    }
}
