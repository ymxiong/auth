package cc.eamon.open.chain.processor;

import cc.eamon.open.chain.ChainContextHolder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 17:09:18
 */
public class ChainInvokeIdProcessor implements ChainKeyProcessor {

    @Override
    public ChainKeyEnum chainKey() {
        return ChainKeyEnum.CHAIN_INVOKE_ID;
    }

    @Override
    public void handle(String key, String value) {
        List<String> invokeIds = new ArrayList<>();
        if (!StringUtils.isEmpty(value)) {
            Collections.addAll(invokeIds, value.split("."));
        }
        invokeIds.add("0");
        ChainContextHolder.put(chainKey(), invokeIds);
    }
}
