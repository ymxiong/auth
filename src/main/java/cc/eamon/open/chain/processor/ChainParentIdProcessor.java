package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;

import java.util.UUID;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/2 20:58
 **/
public class ChainParentIdProcessor extends BaseChainKeyProcessor{
    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.PARENT_ID;
    }

    @Override
    public void init() {

    }

    @Override
    public void handle(String key, Object value) {
        logger.info("PARENT_SPAN => " + chainKey().getKey() + "-" + value);
    }
}
