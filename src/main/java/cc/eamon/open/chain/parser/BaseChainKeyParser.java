package cc.eamon.open.chain.parser;

import cc.eamon.open.chain.parser.metadata.ChainKeyParserMetadata;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/1/2 15:12
 **/
public abstract class BaseChainKeyParser<Type> implements ChainKeyParser<Type>{

    protected static void registry(ChainKeyParserMetadata chainKeyParserMetadata){
        ChainKeyParserMetadata.addChainKeyParser(chainKeyParserMetadata);
    }
}
