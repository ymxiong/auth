package cc.eamon.open.chain.parser.metadata;

import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.ChainKeyParserEnum;
import cc.eamon.open.chain.parser.DefaultMapChainKeyParser;
import cc.eamon.open.chain.parser.map.UserGenericMap;

import java.lang.reflect.TypeVariable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/29 19:53
 **/
//ChainKeyParser包装类，存储默认Parser和用户自定义Parser元信息
public class ChainKeyParserMetadata {

    private static Map<String, ChainKeyParser> chainKeyParserTagMap = new LinkedHashMap<>();

    private static Map<Class, ChainKeyParser> chainKeyParserClassMap = new LinkedHashMap<>();

    static {
        for (ChainKeyParserEnum value : ChainKeyParserEnum.values()) {
            chainKeyParserTagMap.put(value.getParserTag(), value.getParser());
            chainKeyParserClassMap.put(value.getClassType(), value.getParser());
        }
    }

    protected String tag;

    protected Class classType;

    protected ChainKeyParser chainKeyParser;

    public ChainKeyParserMetadata(String tag, Class classType, ChainKeyParser chainKeyParser) {
        this.tag = tag;
        this.classType = classType;
        this.chainKeyParser = chainKeyParser;
    }

    public ChainKeyParserMetadata() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public ChainKeyParser getChainKeyParser() {
        return chainKeyParser;
    }

    public void setChainKeyParser(ChainKeyParser chainKeyParser) {
        this.chainKeyParser = chainKeyParser;
    }

    private static Map<String, ChainKeyParser> getChainKeyParserTagMap() {
        return chainKeyParserTagMap;
    }

    private static Map<Class, ChainKeyParser> getChainKeyParserClassMap() {
        return chainKeyParserClassMap;
    }

    public static void addChainKeyParser(ChainKeyParserMetadata chainKeyParserMetadata) {
        chainKeyParserTagMap.put(chainKeyParserMetadata.getTag(), chainKeyParserMetadata.getChainKeyParser());
        chainKeyParserClassMap.put(chainKeyParserMetadata.getClassType(), chainKeyParserMetadata.getChainKeyParser());

    }

    public static void addGenericChainKeyParser(GenericChainKeyParserMetadata chainKeyParserMetadata) {
        //TODO 是否需要分离
        chainKeyParserTagMap.put(chainKeyParserMetadata.getTag(), chainKeyParserMetadata.getChainKeyParser());
    }

    public static ChainKeyParser getChainKeyParser(Class classType) {
        if (Map.class.isAssignableFrom(classType))
            return ChainKeyParserEnum.DEFAULT_MAP_PARSER.getParser();
        for (Class value : chainKeyParserClassMap.keySet()) {
            if (value.equals(classType))
                return chainKeyParserClassMap.get(value);
        }
        return ChainKeyParserEnum.DEFAULT_PARSER.getParser();
    }

    public static ChainKeyParser getChainKeyParser(String type) {
        if (type == null || "".equals(type))
            return ChainKeyParserEnum.DEFAULT_PARSER.getParser();
        for (String parserTag : chainKeyParserTagMap.keySet()) {
            if (type.equals(parserTag))
                return chainKeyParserTagMap.get(parserTag);
        }
        return ChainKeyParserEnum.DEFAULT_PARSER.getParser();
    }
}
