package cc.eamon.open.chain.parser;

import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/13 20:35
 **/
public class DateChainKeyParser implements ChainKeyParser<Date> {

    @Override
    public String encodeChainContext(Date chainContext) {
        return String.valueOf(chainContext.getTime());
    }

    @Override
    public Date decodeChainContext(String header) {
        return new Date(Long.parseLong(header));
    }
}
