package com.neusiri.husky.core.export.excel.dict;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>字典数据容器</p>
 * <p>创建日期：2019-10-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@UtilityClass
public class DictHolder {

    /**
     * 自定义字典类型容器
     */
    private final static ThreadLocal<Map<String, Map<Object, DictInfo>>> CUSTOM_DICT_CONTEXT = new ThreadLocal<>();


    /**
     * 设置自定义字典数据
     *
     * @param customDict 字典数据
     */
    public static void setCustomDict(Map<String, List<DictInfo>> customDict) {
        if (MapUtils.isNotEmpty(customDict)) {
            Map<String, Map<Object, DictInfo>> dictMap = new HashMap<>(16);
            customDict.forEach((dictType, customDictList) -> {
                Map<Object, DictInfo> dictInfoMap = customDictList.stream().collect(Collectors.toMap(DictInfo::getValue, dict -> dict));
                dictMap.put(dictType, dictInfoMap);
            });
            CUSTOM_DICT_CONTEXT.set(dictMap);
        }

    }

    /**
     * 获取自定义字典数据
     *
     * @return 字典数据
     */
    public static Map<String, Map<Object, DictInfo>> getCustomDict() {
        return CUSTOM_DICT_CONTEXT.get();
    }

    /**
     * 清除方法
     */
    public static void clear() {
        CUSTOM_DICT_CONTEXT.remove();
    }

}
