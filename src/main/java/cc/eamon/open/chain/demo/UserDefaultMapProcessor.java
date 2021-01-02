package cc.eamon.open.chain.demo;

import cc.eamon.open.chain.parser.ChainKeyParserEnum;
import cc.eamon.open.chain.processor.UserBaseChainKeyProcessor;
import cc.eamon.open.chain.processor.metadata.ChainKeyProcessorMetadata;
import org.springframework.stereotype.Component;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/30 21:23
 **/

/**
 * Map<String, String>类型的chainContext可只注册processor如下：
 * 该processor匹配默认的Map<String, String>对应的DefaultMapChainKeyParser
 */

//@Component
//public class UserDefaultMapProcessor extends UserBaseChainKeyProcessor {
//
//    /**
//     * 注册Processor
//     */
//    static {
//        registry(new ChainKeyProcessorMetadata(ChainConst.CHAIN_DEFAULT_MAP, UserDefaultMapProcessor.class));
//    }
//
//    @Override
//    public String getUserParserMapTag() {
//        return ChainKeyParserEnum.DEFAULT_MAP_PARSER.getParserTag();
//    }
//
//    @Override
//    public void handle(String key, Object value) {
//        System.out.println(value);
//    }
//
//    @Override
//    public void init() {
//
//    }
//}
