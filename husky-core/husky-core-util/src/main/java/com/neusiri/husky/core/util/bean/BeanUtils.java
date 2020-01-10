package com.neusiri.husky.core.util.bean;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>bean工具类</p>
 * <p>创建日期：2019-09-10</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class BeanUtils {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    static {

        MODEL_MAPPER.getConfiguration().setFullTypeMatchingRequired(true);
    }

    /**
     * 根据目标对象class生成目标对象,并将源对象的信息copy到目标对象
     *
     * @param source      源对象
     * @param targetClass 目标对象class
     * @param <T>         目标对象泛型
     * @return 生成后的目标对象
     */
    public static <T> T copy(Object source, Class<T> targetClass) {
        return MODEL_MAPPER.map(source, targetClass);
    }

    /**
     * 根据Type生成目标对象,并将源对象的信息copy到目标对象
     *
     * @param source          源对象
     * @param destinationType 目标对象type
     * @return 生成后的目标对象
     */
    public static <T> List<T> copy(Object source, Type destinationType) {
        return MODEL_MAPPER.map(source, destinationType);
    }


}
