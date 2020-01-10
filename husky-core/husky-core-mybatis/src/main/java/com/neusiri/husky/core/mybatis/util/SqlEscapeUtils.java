package com.neusiri.husky.core.mybatis.util;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;

/**
 * <p>sql特殊字符替换工具</p>
 * <p>创建日期：2019-09-26</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class SqlEscapeUtils {

    private static final Escaper escaper;

    static {
        escaper = Escapers.builder().addEscape('_', "\\_")
                .addEscape('\\', "\\\\\\\\")
                .addEscape('\'', "\\'")
                .addEscape('%', "\\%")
                .build();
    }

    /**
     * 转义字符串
     *
     * _ --> \_
     * \ --> \\\\
     * ' --> \'
     * % --> \%
     *
     * @param str 原字符串
     * @return 转义后字符串
     */
    public static String escape(String str) {
        return escaper.escape(str);
    }

}
