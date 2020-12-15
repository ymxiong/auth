package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:42:32
 */
public class ChainThreadCounterProcessor implements ChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.THREAD_COUNTER;
    }
    
    @Override
    public void handle(String key, String value, Class<? extends ChainKeyParser> parserClass) {
        ChainInvokeIdProcessor.PARENT_COUNTER = value;
    }

}
