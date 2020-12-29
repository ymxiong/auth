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
public class ChainTraceIdProcessor extends BaseChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.TRACE_ID;
    }

    @Override
    public void init() {
        String traceID = initID();
        ChainContextHolder.put(chainKey(), traceID);
        logger.info("CHAIN => " + traceID + "-START");
        ChainContextHolder.put(ChainKeyEnum.SPAN_ID, traceID);
        logger.info("SPAN => " + ChainKeyEnum.TRACE_ID.getKey() + "-" + traceID + "::"
                + ChainKeyEnum.SPAN_ID.getKey() + "-" + traceID);
        ChainContextHolder.put(ChainKeyEnum.PARENT_ID, traceID);
    }

    @Override
    public void handle(String key, Object value) {
        ChainContextHolder.put(chainKey(), value);
        logger.info("CHAIN => " + value + "-RUNNING");
    }

    private String initID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
