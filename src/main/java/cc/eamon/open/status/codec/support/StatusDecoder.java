package cc.eamon.open.status.codec.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.error.Assert;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import cc.eamon.open.status.codec.StatusErrorHandler;
import cc.eamon.open.status.codec.prehandler.DecoderPreHandle;
import cc.eamon.open.status.codec.prehandler.support.StatusDecoderPreHandle;
import cc.eamon.open.status.util.StatusUtils;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/30 20:27
 **/
public class StatusDecoder extends Decoder.Default {

    private StatusErrorHandler statusErrorHandler;

    private DecoderPreHandle decoderPreHandle;

    private Decoder delegate;

    private StatusDecoder() {
    }

    public StatusDecoder(Decoder delegate, StatusErrorHandler statusErrorHandler) {
        Assert.notNull(delegate, StatusConstants.STATUS_DEFAULT_ID);
        this.delegate = delegate;
        this.statusErrorHandler = statusErrorHandler;
        this.decoderPreHandle = new StatusDecoderPreHandle();
    }

    /**
     * 将feign包装的response解码为真实返回值
     *
     * @param response
     * @param type
     * @return
     * @throws IOException
     * @throws DecodeException
     * @throws FeignException
     */
    @Override
    public Object decode(Response response, Type type) throws IOException, RuntimeException {
        // type is created by interface metadata not remote actual type
        //责任链，流每次传递借助ByteArrayOutputStream缓冲区

        // 1. 暂存流，并关闭原流
        Response.Body body = response.body();
        ByteArrayOutputStream baos = this.reserveStream(body);

        // 2. 重写流为ByteArrayBody，可复用流
        response = response.toBuilder().body(baos.toByteArray()).build();

        // 3. 解析流
        this.decoderPreHandle.preHandle(response, type);

        int status = response.status();
        String statusString = ChainContextHolder.getString(StatusConstants.STATUS_KEY);
        String message = ChainContextHolder.getString(StatusConstants.MESSAGE_KEY);
        if (this.hasException(statusString, message, status)) {
            String rpcKey = ChainContextHolder.getString(StatusConstants.STATUS_CHAIN_RPC_KEY);
            if (StringUtils.isEmpty(rpcKey))
                throw new StatusException(StatusConstants.DEFAULT_CODE, StatusConstants.DECODE_ERROR_DECODE_MESSAGE);
            throw (RuntimeException) this.statusErrorHandler.handle(StatusUtils.generateErrorMethodKey(rpcKey, statusString));
        }

        // 兼容spring-cloud-openfeign逻辑，走spring converter逻辑
        if (!isOptional(type)) {
            return delegate.decode(response, type);
        }
        if (response.status() == 404 || response.status() == 204) {
            return Optional.empty();
        }
        Type enclosedType = Util.resolveLastTypeParameter(type, Optional.class);
        return Optional.ofNullable(delegate.decode(response, enclosedType));
    }

    private boolean hasException(String statusString, String message, Integer responseStatus) throws IOException {
        if (StringUtils.isEmpty(statusString) || StringUtils.isEmpty(message)) {
            return false;
        }
        // 判断是否发生异常，1. 状态码为错误状态码；2. message中含异常信息
        if (responseStatus >= 300 || Integer.parseInt(statusString) >= 300 || message.contains("exception")) {
            return true;
        }
        return false;

    }

    private ByteArrayOutputStream reserveStream(Response.Body body) throws IOException {
        InputStream inputStream = body.asInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int i;
        while ((i = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, i);
        }
        ensureClose(inputStream);
        return baos;
    }

    private void ensureClose(OutputStream... outputStreams) {
        if (outputStreams == null || outputStreams.length == 0) return;
        for (OutputStream outputStream : outputStreams) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void ensureClose(InputStream... inputStreams) {
        if (inputStreams == null || inputStreams.length == 0) return;
        for (InputStream inputStream : inputStreams) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static boolean isOptional(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getRawType().equals(Optional.class);
    }
}
