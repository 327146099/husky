package com.neusiri.husky.core.util.network;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>url工具类</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class UrlUtils {

    private static AntPathMatcher matcher = new AntPathMatcher("/");

    private static final Set<String> DEFAULT_STATIC_PATH = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico", "/**/swagger-resources/**", "/**/v2/api-docs-ext")));

    /**
     * 请求的路径是否为静态文件
     *
     * @param path 请求路径
     * @return 匹配结果
     */
    public static boolean isStaticPath(String path) {
        for (String pattern : DEFAULT_STATIC_PATH) {
            if (matcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求路径和pattern是否匹配
     *
     * @param pattern pattern
     * @param path    要匹配的路径
     * @return 匹配结果
     */
    public static boolean isMatch(String pattern, String path) {
        return matcher.match(pattern, path);
    }
}
