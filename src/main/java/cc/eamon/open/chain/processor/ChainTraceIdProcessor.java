package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContext;
import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;

import java.util.UUID;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/2 20:58
 **/
public class ChainTraceIdProcessor implements ChainKeyProcessor{
    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.TRACE_ID;
    }

    @Override
    public void handle(String key, String value, Class<? extends ChainKeyParser> parserClass) {
        if(value == null || "".equals(value)) {
            value = UUID.randomUUID().toString().replaceAll("-","");
        }
        ChainContextHolder.put(chainKey(),value);
        logger.info("CHAIN => " + value + "-RUNNING");
    }
}
