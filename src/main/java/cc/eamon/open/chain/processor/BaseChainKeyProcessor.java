package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.metadata.ChainKeyParserMetadata;
import cc.eamon.open.chain.processor.metadata.ChainKeyProcessorMetadata;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/23 19:57
 **/
public abstract class BaseChainKeyProcessor implements ChainKeyProcessor {

    protected static void registry(ChainKeyProcessorMetadata chainKeyProcessorMetadata){
        ChainKeyProcessorMetadata.addChainKeyProcessor(chainKeyProcessorMetadata);
    }

    @Override
    public String parserMapTag() {
        return chainKey() == null ? null : chainKey().getParserMapTag();
    }

    @Override
    public void handle(String key, String value) {
        ChainKeyParser chainKeyParser = ChainKeyParserMetadata.getChainKeyParser(parserMapTag());
        Object chainContext = chainKeyParser.decodeChainContext(value);
        handle(key, chainContext);
    }

    public abstract void handle(String key, Object value);

}
