package cc.eamon.open.chain.demo;

import cc.eamon.open.chain.parser.map.UserGenericMap;
import cc.eamon.open.chain.parser.metadata.GenericChainKeyParserMetadata;

import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/1/2 14:54
 **/
    //指明Map的具体泛型类型，例如此处为<String, Date>，但是须使用ActualMap替换Map，否则无法根据泛型类型匹配parser
    //ChainContextHolder.put(xxx, actualMap);
    //也可使用UserGenericMap的匿名子类如下：
    /**
     * UserGenericMap<String, Date> userGenericMap = new UserGenericMap<String, Date>() {
     *             @Override
     *             protected GenericChainKeyParserMetadata.GenericMetadata genericMetadata() {
     *                 return new GenericChainKeyParserMetadata.GenericMetadata(String.class, Date.class);
     *             }
     *         };
     *         userGenericMap.put("a", new Date(123L));
     *         ChainContextHolder.put(ChainConst.CHAIN_KEY_PREFIX + ChainConst.CHAIN_USER_MAP, userGenericMap);
     */
//public class ActualMap extends UserGenericMap {
//    @Override
//    protected GenericChainKeyParserMetadata.GenericMetadata genericMetadata() {
//        return new GenericChainKeyParserMetadata.GenericMetadata(String.class, Date.class);
//    }
//}
