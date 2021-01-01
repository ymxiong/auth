package cc.eamon.open.chain.processor.metadata;

import cc.eamon.open.chain.parser.metadata.GenericChainKeyParserMetadata;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.chain.processor.ChainKeyProcessor;
import cc.eamon.open.chain.processor.DefaultChainKeyProcessor;
import cc.eamon.open.chain.processor.NotChainKeyProcessor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/29 21:35
 **/
//ChainKeyProcessor包装类，存储默认Processor和用户自定义Processor元信息
public class ChainKeyProcessorMetadata {

    public static Map<String, Class<? extends ChainKeyProcessor>> chainKeyProcessorMap = new LinkedHashMap<>();

    static {
        for (ChainKeyEnum value : ChainKeyEnum.values()) {
            chainKeyProcessorMap.put(value.getKey(), value.getKeyProcessor());
        }
    }

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
    private Class<? extends ChainKeyProcessor> keyProcessor;

    public ChainKeyProcessorMetadata(String key, Class<? extends ChainKeyProcessor> keyProcessor) {
        this.key = key;
        this.keyProcessor = keyProcessor;
    }

//    public ChainKeyProcessorMetadata(String key, String mapKey, Class<? extends ChainKeyProcessor> keyProcessor) {
//        this.key = key;
//        this.mapKey = mapKey;
//        this.keyProcessor = keyProcessor;
//    }

    public String getKey() {
        return KEY_PREFIX + key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public Class<? extends ChainKeyProcessor> getKeyProcessor() {
        return keyProcessor;
    }

    public void setKeyProcessor(Class<? extends ChainKeyProcessor> keyProcessor) {
        this.keyProcessor = keyProcessor;
    }

    public static void addChainKeyProcessor(ChainKeyProcessorMetadata chainKeyProcessorMetadata) {
        chainKeyProcessorMap.put(chainKeyProcessorMetadata.getKey(), chainKeyProcessorMetadata.getKeyProcessor());
    }

    public static Class<? extends ChainKeyProcessor> getKeyProcessor(String key) {
        // check chain key
        boolean isChainKey = isChainKey(key);
        // proc chain enum key & not chain mapping key
        for (String chainKey : chainKeyProcessorMap.keySet()) {
            if (isChainKey && key.equals(chainKey)) {
                return chainKeyProcessorMap.get(key);
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
}
