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
public class ChainSpanIdProcessor extends BaseChainKeyProcessor{
    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.SPAN_ID;
    }

    @Override
    public void init() {

    }

    @Override
    public void handle(String key, Object value) {
        ChainContextHolder.put(ChainKeyEnum.PARENT_ID,value);
        String spanId = UUID.randomUUID().toString().replaceAll("-","");
        ChainContextHolder.put(chainKey(),spanId);
        logger.info("SPAN => " + ChainKeyEnum.SPAN_ID.getKey() + "-" + spanId);
    }
}
