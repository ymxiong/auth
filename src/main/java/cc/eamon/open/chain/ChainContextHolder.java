package cc.eamon.open.chain;

import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.status.StatusException;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-08 00:47:10
 */
public class ChainContextHolder {

    /**
     * init Context for current thread
     */
    private static ThreadLocal<ChainContext> local = ThreadLocal.withInitial(ChainContext::new);

    public ChainContextHolder() {
    }

    public static ChainContext get() {
        if (local.get() == null) {
            local.set(new ChainContext());
        }
        return local.get();
    }

    public static void clear() {
        local.remove();
    }

    public static Object get(String key) {
        return ChainContextHolder.get().get(key);
    }

    public static Object get(ChainKeyEnum chainKeyEnum) {
        return ChainContextHolder.get().get(chainKeyEnum.getKey());
    }

    public static String getString(String key) {
        Object value = get(key);
        if (value instanceof String || value == null) return (String) value;
        throw new StatusException("CHAIN_ERROR");
    }

    public static void put(String key, Object value) {
        ChainContextHolder.get().put(key, value);
    }

    public static void put(ChainKeyEnum chainKeyEnum, Object value) {
        ChainContextHolder.get().put(chainKeyEnum.getKey(), value);
    }

}
