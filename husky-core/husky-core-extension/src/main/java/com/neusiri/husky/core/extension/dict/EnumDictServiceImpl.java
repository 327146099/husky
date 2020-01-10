package com.neusiri.husky.core.extension.dict;

import com.neusiri.husky.core.common.dict.entity.Dict;
import com.neusiri.husky.core.common.dict.entity.DictInfo;
import com.neusiri.husky.core.common.dict.service.IDictService;
import com.neusiri.husky.core.common.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>枚举字典表服务类</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Service("enumDictService")
@Slf4j
public class EnumDictServiceImpl implements IDictService<Object> {

    private Map<String, List<Dict>> enumDictMap = new ConcurrentHashMap<>();

    @Override
    public List<Dict> getDictList(String dictType) {
        if (enumDictMap.containsKey(dictType)) {
            return enumDictMap.get(dictType);
        }
        return null;
    }

    @Override
    public Dict getDictData(String dictType, Object dictValue) {
        if (StringUtils.isEmpty(dictType) || Objects.isNull(dictValue)) {
            return null;
        }
        List<Dict> dictList = getDictList(dictType);
        if (!CollectionUtils.isEmpty(dictList)) {
            for (Dict dict : dictList) {
                if (dictValue.equals(dict.getValue())) {
                    return dict;
                }
            }
        }
        return null;
    }

    @Override
    public List<DictInfo> getDictInfoList() {
        return null;
    }

    @PostConstruct
    public void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始初始化枚举类字典");
        // 扫描获取实现BaseEnum接口的枚举类
        Reflections reflections = new Reflections("com.neusiri.husky");
        Set<Class<? extends BaseEnum>> classSet = reflections.getSubTypesOf(BaseEnum.class);
        for (Class<? extends BaseEnum> clazz : classSet) {
            // 获取字典名称,驼峰标识
            String key = lowerFirstChar(clazz.getSimpleName());
            // 获取枚举类的key和value
            Method getValue = clazz.getMethod("getValue");
            Method getName = clazz.getMethod("getName");

            BaseEnum[] enumConstants = clazz.getEnumConstants();
            List<Dict> enums = new ArrayList<>(enumConstants.length);
            for (BaseEnum enumConstant : enumConstants) {
                String dictName = (String) getName.invoke(enumConstant);
                Object dictValue = getValue.invoke(enumConstant);
                Dict dict = new Dict();
                dict.setLabel(dictName);
                dict.setValue(dictValue);
                dict.setDictType(key);
                enums.add(dict);
            }
            enumDictMap.put(key, enums);
        }
        log.info("枚举类字典初始化完毕, 共加载{}个字典", enumDictMap.size());
    }

    /**
     * 名称第一个字母转小写,转驼峰标识
     *
     * @param str 原名称
     * @return 转换后的名称l
     */
    private String lowerFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


}
