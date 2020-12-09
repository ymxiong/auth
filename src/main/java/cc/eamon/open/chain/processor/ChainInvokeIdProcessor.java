package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 17:09:18
 */
public class ChainInvokeIdProcessor implements ChainKeyProcessor {

    public static String PARENT_COUNTER = null;

    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.CHAIN_INVOKE_ID;
    }

    @Override
    public void handle(String key, String value) {
        StringBuilder invokeId = new StringBuilder(value);
        if (PARENT_COUNTER != null) {
            invokeId.append(PARENT_COUNTER).append(".");
            ChainContextHolder.put(chainKey(), invokeId.toString());
        }
    }
}
