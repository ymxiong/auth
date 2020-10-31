package cc.eamon.open.status;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/10/30 15:28
 **/
public abstract class StatusDecoder implements ErrorDecoder {

    private static Logger logger = LoggerFactory.getLogger(StatusDecoder.class);

    public static final int SERVICE_ERROR = 701;

    /**
     * 替换默认的feign异常处理，否则feign会报错状态码错误
     * @param s
     * @param response
     * @return
     */
    @Override
    public Exception decode(String s, Response response) {
        //识别异常
        StatusException statusException = new StatusException(SERVICE_ERROR,"远程服务调用异常");
        String exceptionContent;
        try{
            //转化异常
            exceptionContent = Util.toString(response.body().asReader());
            if(!StringUtils.isEmpty(exceptionContent)){
                exceptionContent.replaceAll("\n","").replaceAll("\t","");
                JSONObject responseJson = (JSONObject) JSON.parse(exceptionContent);
                if(responseJson != null){
                    int code = Integer.parseInt(responseJson.getString("status"));
                    String msg = responseJson.getString("message");
                    if(!StringUtils.isEmpty(code) && !StringUtils.isEmpty(msg)){
                        statusException = new StatusException(code,msg);
                    }
                }
            }
            return statusException;
        }catch (Exception e){
            logger.error("feign处理异常错误",e);
            return statusException;
        }
    }
}
