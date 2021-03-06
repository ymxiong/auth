package cc.eamon.open.chain.demo;

import cc.eamon.open.chain.parser.MapBaseChainKeyParser;
import cc.eamon.open.chain.parser.metadata.GenericChainKeyParserMetadata;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/1/2 0:52
 **/

/**
 * 自定义UserGenericMap<String, Date>的parser
 */
//@Component
//public class UserMapParser extends MapBaseChainKeyParser<String, Date> {
//
//    static {
//        registry(new GenericChainKeyParserMetadata(
//                ChainConst.CHAIN_USER_MAP_PARSER_TAG,
//                new UserMapParser())
//        );
//    }
//
//    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//    public UserMapParser() {
//        super(new GenericChainKeyParserMetadata.GenericMetadata(String.class, Date.class));
//    }
//
//    @Override
//    public Map<String, String> encodeMapChainContext(Map<String, Date> map) {
//        Map<String, String> map1 = new HashMap<>();
//        for (String key : map.keySet()) {
//            map1.put(key, simpleDateFormat.format(map.get(key)));
//        }
//        return map1;
//    }
//
//    @Override
//    public Map<String, Date> decodeMapChainContext(Map<String, String> stringMap) {
//        Map<String, Date> map1 = new HashMap<>();
//        for (String key : stringMap.keySet()) {
//            try {
//                map1.put(key, simpleDateFormat.parse(stringMap.get(key)));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return map1;
//    }
//}
