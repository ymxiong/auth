package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 23:03:50
 */
public class UserTokenProcessor implements ChainKeyProcessor{
    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.USER_TOKEN;
    }

    @Override
    public void handle(String key, String value) {
        if (value != null) {
            ChainContextHolder.put(chainKey(), value);
        }
    }
}
