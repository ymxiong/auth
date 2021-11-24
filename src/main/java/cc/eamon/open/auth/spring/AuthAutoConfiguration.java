package cc.eamon.open.auth.spring;

import cc.eamon.open.auth.advice.AuthAdvice;
import cc.eamon.open.auth.aop.deserializer.AuthRequestBodyAdvice;
import cc.eamon.open.auth.authenticator.support.DefaultAuthenticator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/2 9:47 下午
 **/
@Configuration
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
