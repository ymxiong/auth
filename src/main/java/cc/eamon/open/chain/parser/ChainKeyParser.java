package cc.eamon.open.chain.parser;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/13 15:50
 **/
public interface ChainKeyParser<ChainType> {

    String encodeChainContext(ChainType chainContext);

    ChainType decodeChainContext(String header);
}
