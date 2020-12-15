package cc.eamon.open.chain.parser;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/13 20:35
 **/
public class LongChainKeyParser implements ChainKeyParser<Long> {

    @Override
    public String encodeChainContext(Long chainContext) {
        return String.valueOf(chainContext);
    }

    @Override
    public Long decodeChainContext(String header) {
        return Long.valueOf(header);
    }
}
