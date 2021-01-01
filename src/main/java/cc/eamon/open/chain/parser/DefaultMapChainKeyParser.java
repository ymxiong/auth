package cc.eamon.open.chain.parser;

import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/30 18:56
 **/
public class DefaultMapChainKeyParser extends MapBaseChainKeyParser<String, String> {

    DefaultMapChainKeyParser(Class k, Class v) {
        super(String.class, String.class);
    }

    @Override
    public Map<String, String> encodeMapChainContext(Map<String, String> map) {
        return map;
    }

    @Override
    public Map<String, String> decodeMapChainContext(Map<String, String> stringMap) {
        return stringMap;
    }
}
