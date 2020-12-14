package cc.eamon.open.chain.parser;

import com.alibaba.fastjson.JSONObject;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/13 20:30
 **/
//其他对象类型
public class DefaultChainKeyParser implements ChainKeyParser {

    @Override
    public String encodeChainContext(Object chainContext) {
        return JSONObject.toJSONString(chainContext);
    }

    @Override
    public Object decodeChainContext(String header) {
        return JSONObject.parse(header);
    }
}
