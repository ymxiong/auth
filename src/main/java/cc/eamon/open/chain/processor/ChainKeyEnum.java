package cc.eamon.open.chain.processor;

/**
 * TODO:NEED OPTIMIZE NEED SUPPORT SORT
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-23 16:37:53
 */
public enum ChainKeyEnum {

    /**
     * time key to record chain first app first thread open time
     */
    CHAIN_OPEN_TIME("OPEN-TIME", "DATE", ChainTimeProcessor.class),

    /**
     * time key to record current app current thread open time
     */
    APP_OPEN_TIME("THREAD-TIME", "DATE", ChainThreadTimeProcessor.class),

    /**
     * invoke key to record the invoke sequence
     */
    CHAIN_INVOKE_ID("THREAD-INVOKE-ID", "STRING", ChainInvokeIdProcessor.class),

    SPAN_ID("SPAN-ID", "STRING", ChainSpanIdProcessor.class),

    PARENT_ID("PARENT-ID", "STRING", ChainParentIdProcessor.class),

    TRACE_ID("TRACE-ID", "STRING", ChainTraceIdProcessor.class),

    THREAD_COUNTER("THREAD-COUNTER", "INTEGER", ChainThreadCounterProcessor.class),

    /**
     * user id key to record user id
     */
//    USER_ID("USER-ID", "userId", new UserIdProcessor()),

    /**
     * user token key to record user token
     */
//    USER_TOKEN("USER-TOKEN", "token", new UserTokenProcessor()),

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
     * chain key map parser tag
     */
    private String parserMapTag;

    /**
     * chain key processor
     */
    private Class<? extends ChainKeyProcessor> keyProcessor;

    ChainKeyEnum(String key, Class<? extends ChainKeyProcessor> keyProcessor) {
        this.key = key;
        this.keyProcessor = keyProcessor;
    }

    ChainKeyEnum(String key, String parserMapTag, Class<? extends ChainKeyProcessor> keyProcessor) {
        this.key = key;
        this.parserMapTag = parserMapTag;
        this.keyProcessor = keyProcessor;
    }

    public String getKey() {
        return KEY_PREFIX + key;
    }

    public Class<? extends ChainKeyProcessor> getKeyProcessor() {
        return keyProcessor;
    }

    public String getParserMapTag() {
        return parserMapTag;
    }

    public void setParserMapTag(String parserMapTag) {
        this.parserMapTag = parserMapTag;
    }

    public static Class<? extends ChainKeyProcessor> getKeyProcessor(String key) {
        // check chain key
        boolean isChainKey = isChainKey(key);
        // proc chain enum key & not chain mapping key
        for (ChainKeyEnum value : ChainKeyEnum.values()) {
            if (isChainKey && key.equals(value.getKey())) {
                return value.getKeyProcessor();
            } else if (!isChainKey && key != null && key.equals(value.getParserMapTag())) {
                return value.getKeyProcessor();
            }
        }
        // proc chain not enum key & other cases
        if (isChainKey) {
            return DefaultChainKeyProcessor.class;
        } else {
            return NotChainKeyProcessor.class;
        }
    }

    public static boolean isChainKey(String key) {
        if (key == null) return false;
        return key.startsWith(KEY_PREFIX);
    }

    public static boolean isDefaultChainKey(String key) {
        if (key == null) return false;
        for (ChainKeyEnum chainKeyEnum : ChainKeyEnum.values()) {
            if (chainKeyEnum.getKey().equals(key))
                return true;
        }
        return false;
    }

}
