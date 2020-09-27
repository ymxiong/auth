package cc.eamon.open.chain.processor;

/**
 * TODO:NEED OPTIMIZE
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:37:53
 */
public enum ChainKeyEnum {

    /**
     * time key to record chain first app first thread open time
     */
    CHAIN_OPEN_TIME("OPEN-TIME", new ChainTimeProcessor()),

    /**
     * time key to record current app current thread open time
     */
    APP_OPEN_TIME("THREAD-TIME", new ThreadTimeProcessor()),

    /**
     * invoke key to record the invoke sequence
     */
    CHAIN_INVOKE_ID("THREAD-INVOKE-ID", new ChainInvokeIdProcessor()),

    /**
     * user id key to record user id
     */
    USER_ID("USER-ID", "userId", new UserIdProcessor()),

    /**
     * user token key to record user token
     */
    USER_TOKEN("USER-TOKEN", "token", new UserTokenProcessor()),

    ;

    /**
     * prefix of all keys
     */
    private static final String KEY_PREFIX = "CHAIN-";


    /**
     * chain key
     */
    private String key;

    /**
     * chain key map key
     */
    private String mapKey;

    /**
     * chain key processor
     */
    private ChainKeyProcessor keyProcessor;

    ChainKeyEnum(String key, ChainKeyProcessor keyProcessor) {
        this.key = key;
        this.keyProcessor = keyProcessor;
    }

    ChainKeyEnum(String key, String mapKey, ChainKeyProcessor keyProcessor) {
        this.key = key;
        this.mapKey = mapKey;
        this.keyProcessor = keyProcessor;
    }

    public String getKey() {
        return KEY_PREFIX + key;
    }

    public ChainKeyProcessor getKeyProcessor() {
        return keyProcessor;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public static ChainKeyProcessor getKeyProcessor(String key) {
        // check chain key
        boolean isChainKey = isChainKey(key);
        // proc chain enum key & not chain mapping key
        for (ChainKeyEnum value : ChainKeyEnum.values()) {
            if (isChainKey && key.equals(value.getKey())) {
                return value.getKeyProcessor();
            } else if (!isChainKey && key != null && key.equals(value.getMapKey())) {
                return value.getKeyProcessor();
            }
        }
        // proc chain not enum key & other cases
        if (isChainKey) {
            return new DefaultChainKeyProcessor();
        } else {
            return new NotChainKeyProcessor();
        }
    }


    private static boolean isChainKey(String key) {
        if (key == null) return false;
        return key.startsWith(KEY_PREFIX);
    }


}
