package com.neusiri.husky.core.boot.converter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>字符串TO时间类型转换器</p>
 * <p>创建日期：2019-08-16</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(@Nullable String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        Date date = null;
        try {
            date = DateUtils.parseDate(source, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss");

        } catch (ParseException e) {
            log.error("绑定日期参数转化异常", e);
        }

        return date;

    }
}
