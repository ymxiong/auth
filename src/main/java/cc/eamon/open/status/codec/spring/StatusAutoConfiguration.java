package cc.eamon.open.status.codec.spring;

import cc.eamon.open.chain.ChainContextAdvice;
import cc.eamon.open.status.codec.StatusErrorHandler;
import cc.eamon.open.status.codec.support.DefaultStatusErrorHandler;
import cc.eamon.open.status.codec.support.StatusDecoder;
import cc.eamon.open.status.codec.support.StatusErrorDecoder;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/2 11:38
 **/
@Configuration
@Import(StatusErrorDecoderRegistrar.class)
public class StatusAutoConfiguration {

    @Autowired
    StatusErrorHandler statusErrorHandler;

    @Bean
    public StatusErrorHandler statusErrorHandler() {
        return new DefaultStatusErrorHandler();
    }

    @Bean
    public Decoder decoder() {
        return new StatusDecoder(statusErrorHandler);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new StatusErrorDecoder(statusErrorHandler);
    }

    @ConditionalOnMissingBean(ChainContextAdvice.class)
    @Bean
    public ChainContextAdvice chainContextAdvice() {
        return new ChainContextAdvice();
    }
}
