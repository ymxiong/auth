package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.DateChainKeyParser;
import cc.eamon.open.error.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 17:09:18
 */
public class ChainTimeProcessor extends BaseChainKeyProcessor {


    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.CHAIN_OPEN_TIME;
    }

    @Override
    public void init() {
        Date openTime = new Date();
        ChainContextHolder.put(ChainKeyEnum.CHAIN_OPEN_TIME, openTime);
        ChainContextHolder.put(ChainKeyEnum.APP_OPEN_TIME, openTime);
        logger.info("CHAIN_OPEN_TIME => " + openTime);
    }

    @Override
    public void handle(String key, Object value) {
        Date date = null;
        Object chainValue = ChainContextHolder.get(chainKey());
        if (chainValue != null) {
            date = (Date) chainValue;
            logger.info(chainKey().getKey() + " => " + date);
            return;
        }
        if (date == null) {
            Object appTime = ChainContextHolder.get(ChainKeyEnum.APP_OPEN_TIME);
            if (appTime != null)
                date = (Date) appTime;
            if (date == null) {
                date = new Date();
            }
        }
        ChainContextHolder.put(chainKey(), date);
        logger.info(chainKey().getKey() + " => " + date);
    }
}
