package cc.eamon.open.chain.parser.metadata;

import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.ChainKeyParserEnum;
import cc.eamon.open.chain.parser.MapBaseChainKeyParser;
import cc.eamon.open.error.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/30 23:47
 **/
//涉及泛型的类型parser，如Map
public class GenericChainKeyParserMetadata extends ChainKeyParserMetadata {

    private static Map<GenericMetadata, ChainKeyParser> genericMetadataMap = new HashMap<>();

    private String genericTag;

    private GenericMetadata genericMetadata;

    public GenericChainKeyParserMetadata(String tag, Class classType, ChainKeyParser chainKeyParser) {
        super(tag, classType, chainKeyParser);
        MapBaseChainKeyParser mapBaseChainKeyParser = (MapBaseChainKeyParser) chainKeyParser;
        this.genericMetadata = new GenericMetadata(mapBaseChainKeyParser.getK(), mapBaseChainKeyParser.getV());
        this.genericTag = tag;
    }

    public GenericMetadata getGenericMetadata() {
        return genericMetadata;
    }

    public void setGenericMetadata(GenericMetadata genericMetadata) {
        this.genericMetadata = genericMetadata;
    }

    public String getGenericTag() {
        return genericTag;
    }

    public void setGenericTag(String genericTag) {
        this.genericTag = genericTag;
    }

    public static void addChainKeyParser(GenericChainKeyParserMetadata genericChainKeyParserMetadata) {
        Assert.isNull(genericMetadataMap.get(genericChainKeyParserMetadata.getGenericTag()), "CHAIN_PARSE_ERROR");
        ChainKeyParserMetadata.addGenericChainKeyParser(genericChainKeyParserMetadata);
        genericMetadataMap.put(genericChainKeyParserMetadata.getGenericMetadata(), genericChainKeyParserMetadata.getChainKeyParser());
    }

    /**
     * 解决泛型类型匹配encode匹配错误问题
     *
     * @param genericMetadata
     * @return
     */
    public static ChainKeyParser getChainKeyParser(GenericMetadata genericMetadata) {
        for (GenericMetadata value : genericMetadataMap.keySet()) {
            if (value.equals(genericMetadata))
                return genericMetadataMap.get(value);
        }
        return ChainKeyParserEnum.DEFAULT_PARSER.getParser();
    }

    public static class GenericMetadata {
        private Class para1;

        private Class para2;

        public GenericMetadata(Class para1, Class para2) {
            this.para1 = para1;
            this.para2 = para2;
        }

        public Class getPara1() {
            return para1;
        }

        public void setPara1(Class para1) {
            this.para1 = para1;
        }

        public Class getPara2() {
            return para2;
        }

        public void setPara2(Class para2) {
            this.para2 = para2;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof GenericMetadata) || obj == null)
                return false;
            GenericMetadata genericMetadata = (GenericMetadata) obj;
            if (genericMetadata.para1.getName().equals(this.para1.getName()) &&
                    genericMetadata.para2.getName().equals(this.para2.getName()))
                return true;
            return false;
        }
    }
}
