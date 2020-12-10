package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;

import java.util.UUID;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/2 20:58
 **/
public class ChainParentIdProcessor implements ChainKeyProcessor{
    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.PARENT_ID;
    }

    @Override
    public void handle(String key, String value) {
        logger.info("PARENT_SPAN => " + ChainKeyEnum.PARENT_ID.getKey() + "-" + value);
    }
}
