package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.metadata.ChainKeyParserMetadata;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/23 19:57
 **/
public abstract class BaseChainKeyProcessor implements ChainKeyProcessor {

    @Override
    public String mapKey() {
        return chainKey() == null ? null : chainKey().getMapKey();
    }

    @Override
    public void handle(String key, String value) {
        ChainKeyParser chainKeyParser = ChainKeyParserMetadata.getChainKeyParser(mapKey());
        Object chainContext = chainKeyParser.decodeChainContext(value);
        handle(key, chainContext);
    }

    public abstract void handle(String key, Object value);

}
