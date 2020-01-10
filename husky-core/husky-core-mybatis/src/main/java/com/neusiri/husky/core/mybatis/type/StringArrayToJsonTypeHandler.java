package com.neusiri.husky.core.mybatis.type;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>json类型处理器</p>
 * <p>创建日期：2019-10-11</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
public class StringArrayToJsonTypeHandler extends JsonTypeHandler<String[]> {

    public StringArrayToJsonTypeHandler() {
        super(String[].class);
    }
}
