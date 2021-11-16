package cc.eamon.open.spring;

import cc.eamon.open.auth.advice.AuthAdvice;
import cc.eamon.open.auth.aop.deserializer.AuthRequestBodyAdvice;
import cc.eamon.open.auth.authenticator.support.DefaultAuthenticator;
import cc.eamon.open.status.codec.spring.StatusAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/2 9:47 下午
 **/
@Configuration
@Import(StatusAutoConfiguration.class)
public class AuthAutoConfiguration {

    @ConditionalOnMissingBean(AuthAdvice.class)
    @Bean
    public AuthAdvice authAdvice() {
        return new DefaultAuthenticator();
    }

    @Bean
    public AuthRequestBodyAdvice authRequestBodyAdvice() {
        return new AuthRequestBodyAdvice();
    }
}
