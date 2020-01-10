package com.neusiri.husky.core.export.excel.dict;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.neusiri.husky.core.common.dict.constant.DictConstants;
import com.neusiri.husky.core.common.dict.entity.Dict;
import com.neusiri.husky.core.common.dict.service.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>excel导出字典表数据处理器</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Component
@Slf4j
public class GlobalExcelDictHandler implements IExcelDictHandler {

    @Autowired
    @Qualifier("dataSourceDictService")
    private IDictService<String> dataSourceDictService;

    @Autowired
    @Qualifier("enumDictService")
    private IDictService<Object> enumDictService;

    @Override
    public String toName(String dict, Object obj, String name, Object value) {

        Dict dictData = null;
        // 如果dict没有以enum#开头,说明以数据库方式提供
        if (dict.startsWith(DictConstants.EXCEL_ENUM_DICT_PREFIX)) {
            //获取枚举类的class全路径
            String enumDictType = dict.substring(5);
            dictData = enumDictService.getDictData(enumDictType, value);
        }
        // 如果是自定义字典数据
        else if (dict.startsWith(DictConstants.EXCEL_CUSTOM_DICT_PREFIX)) {
            // 获取自定义字典类型名称
            String customDictType = dict.substring(7);
            Map<String, Map<Object, DictInfo>> customDict = DictHolder.getCustomDict();
            if (MapUtils.isEmpty(customDict) || !customDict.containsKey(customDictType)) {
                log.warn("自定义字典类型数据[{}]不存在, 请检查是否传递数据", customDictType);
                return String.valueOf(value);
            }
            Map<Object, DictInfo> dictInfoMap = customDict.get(customDictType);
            DictInfo dictInfo = dictInfoMap.get(value);
            if (dictInfo == null) {
                return String.valueOf(value);
            }
            return dictInfo.getName();

        } else {
            dictData = dataSourceDictService.getDictData(dict, value.toString());
        }
        if (dictData != null) {
            return dictData.getLabel();
        }

        return null;
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        return null;
    }

}
