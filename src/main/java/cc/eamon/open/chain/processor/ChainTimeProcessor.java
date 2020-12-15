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
public class ChainTimeProcessor implements ChainKeyProcessor {


    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.CHAIN_OPEN_TIME;
    }

    @Override
    public void handle(String key, String value, Class<? extends ChainKeyParser> parserClass) {
        Date date = null;
        Object chainValue = ChainContextHolder.get(chainKey());
        if(chainValue != null){
            date = (Date) chainValue;
            logger.info(chainKey().getKey() + " => " + date);
            return;
        }
        Class<? extends ChainKeyParser> parserClazz = ChainKeyEnum.getKeyParser(key);
        DateChainKeyParser parser = null;
        try {
            parser = (DateChainKeyParser) parserClazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            Assert.notNull(null, "CHAIN_PARSE_ERROR");
        }
        if (!StringUtils.isEmpty(value)) {
            date = parser.decodeChainContext(value);
        }
        if (date == null) {
            Object appTime = ChainContextHolder.get(ChainKeyEnum.APP_OPEN_TIME);
            if(appTime != null)
                date = (Date) appTime;
            if (date == null) {
                date = new Date();
            }
        }
        ChainContextHolder.put(chainKey(), date);
        logger.info(chainKey().getKey() + " => " + date);
    }
}
