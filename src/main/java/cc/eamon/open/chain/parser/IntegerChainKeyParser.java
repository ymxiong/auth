package cc.eamon.open.chain.parser;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/13 20:35
 **/
public class IntegerChainKeyParser implements ChainKeyParser<Integer> {

    @Override
    public String encodeChainContext(Integer chainContext) {
        return String.valueOf(chainContext);
    }

    @Override
    public Integer decodeChainContext(String header) {
        return Integer.valueOf(header);
    }
}
