package cc.eamon.open.chain.parser;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/24 0:07
 **/
public class StringChainKeyParser implements ChainKeyParser {
    @Override
    public String encodeChainContext(Object chainContext) {
        return (String) chainContext;
    }

    @Override
    public Object decodeChainContext(String header) {
        return header;
    }
}
