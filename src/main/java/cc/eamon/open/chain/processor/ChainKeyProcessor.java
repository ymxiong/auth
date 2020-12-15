package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.parser.ChainKeyParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:42:17
 */
public interface ChainKeyProcessor {

    Logger logger = LoggerFactory.getLogger(ChainKeyProcessor.class);

    ChainKeyEnum chainKey();

    void handle(String key, String value, Class<? extends ChainKeyParser> parserClass);


}
