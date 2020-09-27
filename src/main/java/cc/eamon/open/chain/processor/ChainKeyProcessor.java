package cc.eamon.open.chain.processor;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:42:17
 */
public interface ChainKeyProcessor {

    ChainKeyEnum chainKey();

    void handle(String key, String value);


}
