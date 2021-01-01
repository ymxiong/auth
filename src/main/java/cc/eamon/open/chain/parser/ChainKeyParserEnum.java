package cc.eamon.open.chain.parser;

import java.util.Date;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/13 20:26
 **/
public enum ChainKeyParserEnum {

    DEFAULT_PARSER("DEFAULT", Object.class, new DefaultChainKeyParser()),

    DATE_PARSER("DATE", Date.class, new DateChainKeyParser()),

    INTEGER_PARSER("INTEGER", Integer.class, new IntegerChainKeyParser()),

    LONG_PARSER("LONG", Long.class, new LongChainKeyParser()),

    STRING_PARSER("STRING", String.class, new StringChainKeyParser()),

    DEFAULT_MAP_PARSER("MAP", Map.class, new DefaultMapChainKeyParser(String.class, String.class));

    private String tag;

    private Class classType;

    private ChainKeyParser parser;

    ChainKeyParserEnum(String tag, Class classType, ChainKeyParser parser) {
        this.tag = tag;
        this.classType = classType;
        this.parser = parser;
    }

    ChainKeyParserEnum(Class classType, ChainKeyParser parser) {
        this.classType = classType;
        this.parser = parser;
    }

    public String getParserTag() {
        return tag;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public ChainKeyParser getParser() {
        return parser;
    }

    public void setParser(ChainKeyParser parser) {
        this.parser = parser;
    }

    public static ChainKeyParser getChainKeyParser(Class classType) {
        for (ChainKeyParserEnum value : ChainKeyParserEnum.values()) {
            if (value.classType.equals(classType))
                return value.getParser();
        }
        return DEFAULT_PARSER.getParser();
    }

    public static ChainKeyParser getChainKeyParser(String type) {
        if (type == null || "".equals(type))
            return DEFAULT_PARSER.getParser();
        for (ChainKeyParserEnum value : ChainKeyParserEnum.values()) {
            if (type.startsWith(value.getParserTag()))
                return value.getParser();
        }
        return DEFAULT_PARSER.getParser();
    }

}
