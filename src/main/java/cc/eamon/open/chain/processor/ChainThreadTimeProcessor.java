package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.DateChainKeyParser;
import cc.eamon.open.error.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 17:09:18
 */
public class ChainThreadTimeProcessor extends BaseChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.APP_OPEN_TIME;
    }

    @Override
    public void init() {

    }

    @Override
    public void handle(String key, Object value) {
        Date date = null;
        Object time = ChainContextHolder.get(chainKey());
        if(time != null){
            date = (Date) time;
            logger.info(chainKey().getKey() + " => " + date);
            return;
        }
        if (date == null) {
            Object openTime = ChainContextHolder.get(ChainKeyEnum.CHAIN_OPEN_TIME);
            if(openTime != null)
                date = (Date) openTime;
            if (date == null) {
                date = new Date();
            }
        }
        ChainContextHolder.put(chainKey(), date);
        logger.info(chainKey().getKey() + " => " + date);
    }


}
