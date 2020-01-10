package com.neusiri.husky.core.util.biz;

import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * <p>uuid工具类</p>
 * <p>创建日期：2019-08-21</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@NoArgsConstructor
public class UUIDUtils {

    /**
     * 获取uuid
     *
     * @return uuid字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
