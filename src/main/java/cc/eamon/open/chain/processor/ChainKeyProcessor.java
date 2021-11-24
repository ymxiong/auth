package cc.eamon.open.chain.processor;

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

    String parserMapTag();

    void init();

    void handle(String key, String value);


}
