package com.neusiri.husky.core.util.http;

import com.neusiri.husky.core.common.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>http工具类</p>
 * <p>创建日期：2019-09-05</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
@Component
public class HttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String QUERY_PARAM_CHART = "?";

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.87 Safari/537.36";

    private static final OkHttpClient OK_HTTP_CLIENT;

    static {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> log.debug("OKHttp-----{}", message));
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request completeRequest = original.newBuilder()
                            .addHeader("User-Agent", USER_AGENT)
                            .build();
                    return chain.proceed(completeRequest);
                })
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String get(String url, Map<String, ?> paramMap) throws IOException {

        String targetUlr = url;
        if (MapUtils.isNotEmpty(paramMap)) {
            StringBuilder sb = new StringBuilder(targetUlr);
            if (!targetUlr.contains(QUERY_PARAM_CHART)) {
                sb.append(QUERY_PARAM_CHART);
            } else {
                sb.append("&");
            }
            boolean first = true;
            for (Map.Entry<String, ?> item : paramMap.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    sb.append("&");
                }
                sb.append(item.getKey())
                        .append("=")
                        .append(item.getValue().toString());
            }

            targetUlr = sb.toString();
        }
        log.info("sendGet - {}", targetUlr);

        Request request = new Request.Builder()
                .url(targetUlr)
                .get().build();
        Call call = OK_HTTP_CLIENT.newCall(request);
        Response execute = call.execute();

        return handleResponse(execute);
    }

    /**
     * 向指定 URL 发送GET方法的请求,并将返回的json类型结果转换为对象
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @param clazz    json目标对象
     * @return 请求结果
     * @throws IOException IOException
     */
    public static <T> T get(String url, Map<String, ?> paramMap, Class<T> clazz) throws IOException {
        String result = get(url, paramMap);
        return parseJson(result, clazz);
    }

    /**
     * 向指定 URL 发送GET方法的请求, 并将返回的json类型结果转换为集合
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @param clazz    json目标对象
     * @return 请求结果
     * @throws IOException IOException
     */
    public static <T> List<T> getList(String url, Map<String, ?> paramMap, Class<T> clazz) throws IOException {
        String result = get(url, paramMap);
        return parseJsonList(result, clazz);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String post(String url, Map<String, ?> paramMap) throws IOException {
        RequestBody requestBody = setRequestBody(paramMap);
        return post(url, requestBody);
    }

    /**
     * 向指定 URL 发送POST方法的请求,请求类型为form,并将返回的json类型结果转换为对象
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 请求结果
     * @throws IOException IOException
     */
    public static <T> T post(String url, Map<String, ?> paramMap, Class<T> clazz) throws IOException {
        String result = post(url, paramMap);

        return parseJson(result, clazz);
    }

    /**
     * 向指定 URL 发送POST方法的请求,请求类型为form,并将返回的json类型结果转换为对象集合
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 请求结果集合
     * @throws IOException IOException
     */
    public static <T> List<T> postList(String url, Map<String, ?> paramMap, Class<T> clazz) throws IOException {
        String result = post(url, paramMap);

        return parseJsonList(result, clazz);
    }

    /**
     * 向指定 URL 发送POST方法的请求,请求类型为json
     *
     * @param url    请求地址
     * @param object 请求参数
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String postJson(String url, Object object) throws IOException {

        if (Objects.isNull(object)) {
            return null;
        }
        String jsonString = com.alibaba.fastjson.JSON.toJSONString(object);
        RequestBody requestBody = RequestBody.create(jsonString, JSON);
        return post(url, requestBody);
    }

    /**
     * 向指定 URL 发送POST方法的请求,请求类型为json,并将请求结果转换为实体集合
     *
     * @param url    请求地址
     * @param object 请求参数
     * @return 请求结果
     * @throws IOException IOException
     */
    public static <T> T postJson(String url, Object object, Class<T> clazz) throws IOException {

        String result = postJson(url, object);

        return parseJson(result, clazz);
    }

    /**
     * 向指定 URL 发送POST方法的请求,请求类型为json,并将请求结果转换为实体集合
     *
     * @param url    请求地址
     * @param object 请求参数
     * @return 请求结果集合
     * @throws IOException IOException
     */
    public static <T> List<T> postJsonList(String url, Object object, Class<T> clazz) throws IOException {

        String result = postJson(url, object);

        return parseJsonList(result, clazz);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param text  json对象
     * @param clazz 对象类型
     * @param <T>   对象类型
     * @return 转换后的对象
     */
    private static <T> T parseJson(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        return com.alibaba.fastjson.JSON.parseObject(text, clazz);
    }

    /**
     * 将json字符串转换为对象集合
     *
     * @param text  json对象
     * @param clazz 对象类型
     * @param <T>   对象类型
     * @return 转换后的对象集合
     */
    private static <T> List<T> parseJsonList(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        return com.alibaba.fastjson.JSON.parseArray(text, clazz);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url         请求地址
     * @param requestBody 请求体
     * @return 请求结果
     * @throws IOException IOException
     */
    private static String post(String url, RequestBody requestBody) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);

        if (requestBody != null) {
            builder.post(requestBody);
        }

        Request request = builder.build();

        log.info("sendPost - {}", url);

        Call call = OK_HTTP_CLIENT.newCall(request);
        Response execute = call.execute();
        return handleResponse(execute);
    }

    /**
     * 处理返回结果
     *
     * @param execute Response
     * @return 返回结果
     * @throws IOException IOException
     */
    private static String handleResponse(Response execute) throws IOException {
        ResponseBody body = execute.body();
        if (200 != execute.code()) {
            throw new ServiceException("返回错误,请求码为" + execute.code());
        }
        if (body != null) {
            String result = body.string();
            log.debug("recv - {}", result);
            return result;
        }
        return null;
    }

    /**
     * 生成表单请求参数
     *
     * @param bodyParams 表单参数
     * @return RequestBody
     */
    private static RequestBody setRequestBody(Map<String, ?> bodyParams) {
        if (MapUtils.isEmpty(bodyParams)) {
            return null;
        }
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        bodyParams.forEach(((key, value) -> formEncodingBuilder.add(key, value.toString())));
        return formEncodingBuilder.build();
    }

}
