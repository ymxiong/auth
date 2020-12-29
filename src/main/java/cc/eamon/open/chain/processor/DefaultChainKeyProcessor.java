package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContext;
import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:42:32
 */
public class DefaultChainKeyProcessor extends BaseChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void handle(String key, Object value) {
        if (StringUtils.isEmpty(value)) return;
        ChainContextHolder.put(key, value);
    }


}
