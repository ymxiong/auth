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
public class DefaultChainKeyProcessor implements ChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return null;
    }

    @Override
    public void handle(String key, String value, Class<? extends ChainKeyParser> parserClass) {
        if (StringUtils.isEmpty(value)) return;
        if (parserClass == null) {
            ChainContextHolder.put(key, value);
            return;
        }
        ChainKeyParser parser = null;
        try {
            parser = parserClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
        }
        ChainContextHolder.put(key, parser.decodeChainContext(value));
    }



}
