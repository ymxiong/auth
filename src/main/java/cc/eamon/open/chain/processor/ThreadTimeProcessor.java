package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;

import java.util.Date;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 17:09:18
 */
public class ThreadTimeProcessor implements ChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.APP_OPEN_TIME;
    }

    @Override
    public void handle(String key, String value) {
        Date date = (Date) ChainContextHolder.get(chainKey());
        if (date != null) return;
        date = (Date) ChainContextHolder.get(ChainKeyEnum.CHAIN_OPEN_TIME);
        if (date == null) {
            date = new Date();
        }
        ChainContextHolder.put(chainKey(), date);
    }


}
