package com.neusiri.husky.core.export.excel.entity;

import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import com.neusiri.husky.core.export.excel.dict.DictInfo;
import com.neusiri.husky.core.export.excel.style.HuskyCellStyle;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <p>导出参数</p>
 * <p>创建日期：2019-10-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@Accessors(chain = true)
public class ExportParam {

    private Class<? extends IExcelExportStyler> exportStyle;

    /**
     * 导出样式信息
     */
    private Map<String, HuskyCellStyle> cellStyleMap;

    /**
     * 自定义字典信息
     */
    private Map<String, List<DictInfo>> customDict;

    /**
     * 是否添加索引
     */
    private boolean addIndex = true;

}
