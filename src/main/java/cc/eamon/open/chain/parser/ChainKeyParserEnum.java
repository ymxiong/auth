package cc.eamon.open.chain.parser;

import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/13 20:26
 **/
public enum ChainKeyParserEnum {

    DEFAULT_PARSER(Object.class, DefaultChainKeyParser.class),

    DATE_PARSER("DATE-", Date.class, DateChainKeyParser.class),

    INTEGER_PARSER("INTEGER-", Integer.class, IntegerChainKeyParser.class),

    LONG_PARSER("LONG-", Long.class, LongChainKeyParser.class),

    STRING_PARSER("STRING-", String.class, null)
    ;

    private String tag;

    private Class classType;

    private Class<? extends ChainKeyParser> parser;

    private static final String CHAIN_PREFIX = "CHAIN-";

    ChainKeyParserEnum(String tag, Class classType, Class<? extends ChainKeyParser> parser){
        this.tag = tag;
        this.classType = classType;
        this.parser = parser;
    }

    ChainKeyParserEnum(Class classType, Class<? extends ChainKeyParser> parser){
        this.classType = classType;
        this.parser = parser;
    }

    public String getChainKeyTag(){ return CHAIN_PREFIX + tag; }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Class<? extends ChainKeyParser> getParser() {
        return parser;
    }

    public void setParser(Class<? extends ChainKeyParser> parser) {
        this.parser = parser;
    }

    public static Class<? extends ChainKeyParser> getChainKeyParser(Class classType){
        if(classType.equals(String.class)) return null;
        for (ChainKeyParserEnum value : ChainKeyParserEnum.values()) {
            if(value.classType.equals(classType))
                return value.getParser();
        }
        return DefaultChainKeyParser.class;
    }

    public static Class<? extends ChainKeyParser> getChainKeyParser(String tag){
        for (ChainKeyParserEnum value : ChainKeyParserEnum.values()) {
            if (tag.startsWith(value.getChainKeyTag()))
                return value.getParser();
        }
        return DefaultChainKeyParser.class;
    }

}
