package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:42:32
 */
public class ChainThreadCounterProcessor extends BaseChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.THREAD_COUNTER;
    }

    @Override
    public void init() {
        ChainContextHolder.put(chainKey(),0);
    }

    @Override
    public void handle(String key, Object value) {
        ChainInvokeIdProcessor.PARENT_COUNTER = (Integer) value;
    }

}
