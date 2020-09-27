package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import org.springframework.util.StringUtils;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:42:32
 */
public class DefaultChainKeyProcessor implements ChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return null;
    }

    @Override
    public void handle(String key, String value) {
        if (StringUtils.isEmpty(value)) return;
        ChainContextHolder.put(key, value);
    }



}
