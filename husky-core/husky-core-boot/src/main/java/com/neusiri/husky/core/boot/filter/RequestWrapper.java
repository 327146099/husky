package com.neusiri.husky.core.boot.filter;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>自定义request,body内容可重复读</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
@Getter
public class RequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    private InputStream inputStream;

    private String body;

    RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        if (!CollectionUtils.isEmpty(request.getParameterMap())) {
            this.params.putAll(request.getParameterMap());
        }

        dealRequestBody(request);
    }

    /**
     * 获取所有的参数名
     *
     * @return Map的key -> 参数名
     */
    @Override
    public Enumeration<String> getParameterNames() {
        log.debug("getParameterNames: [{}]", JSON.toJSONString(Collections.enumeration(params.keySet())));
        return Collections.enumeration(params.keySet());
    }

    /**
     * 根据参数名获取参数值
     *
     * @param name 参数名
     * @return 参数传入值
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        log.debug("getParameter, name: [{}], value: [{}]", name, values[0]);
        return values[0];
    }

    /**
     * 根据参数名获取所有的参数值
     *
     * @param name 参数名
     * @return 参数所有的值
     */
    @Override
    public String[] getParameterValues(String name) {
        log.debug("getParameterValues, name: [{}], value: [{}]", name, params.get(name));
        return params.get(name);
    }

    /**
     * 获取所有的请求参数
     *
     * @return 请求参数
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        log.debug("getParameterMap, parameterMap: [{}]", JSON.toJSONString(params));
        return this.params;
    }

    /**
     * 获取body的数据流
     *
     * @return {@link DefaultServletInputStream}
     * @throws IOException io异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return Objects.isNull(inputStream) ? null : new DefaultServletInputStream(inputStream);
    }

    /**
     * 获取body内容的数据流
     *
     * @return {@link BufferedReader}
     * @throws IOException io异常
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return Objects.isNull(inputStream) ? null : new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * 处理请求body的内容
     *
     * @param request 请求
     */
    private void dealRequestBody(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        if (Objects.isNull(inputStream)) {
            return;
        }

        String inputStr = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        if (StringUtils.isEmpty(inputStr)) {
            return;
        }

        body = inputStr;

        this.inputStream = IOUtils.toInputStream(inputStr, StandardCharsets.UTF_8);

    }
}

class DefaultServletInputStream extends ServletInputStream {
    private final InputStream sourceStream;
    private boolean finished = false;

    DefaultServletInputStream(InputStream sourceStream) {
        this.sourceStream = sourceStream;
    }

    @Override
    public int read() throws IOException {
        int data = this.sourceStream.read();
        if (data == -1) {
            this.finished = true;
        }

        return data;
    }

    @Override
    public int available() throws IOException {
        return this.sourceStream.available();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.sourceStream.close();
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }
}
