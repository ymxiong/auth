package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;
import org.springframework.util.StringUtils;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:42:32
 */
public class NotChainKeyProcessor implements ChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return null;
    }
    
    @Override
    public void handle(String key, String value, Class<? extends ChainKeyParser> parserClass) {

    }

}
