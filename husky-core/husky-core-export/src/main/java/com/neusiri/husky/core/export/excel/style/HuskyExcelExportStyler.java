package com.neusiri.husky.core.export.excel.style;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>excel导出自定义样式</p>
 * <p>创建日期：2019-09-27</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class HuskyExcelExportStyler extends ExcelExportStylerDefaultImpl {

    /**
     * cssStyle容器
     */
    private static final ThreadLocal<Map<String, HuskyCellStyle>> HUSKY_CELL_STYLE_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, CellStyle>> CELL_STYLE_CONTEXT = new ThreadLocal<>();

    public HuskyExcelExportStyler(Workbook workbook) {
        super(workbook);
    }

    public static void setCellStyle(Map<String, HuskyCellStyle> cellStyleMap) {
        HUSKY_CELL_STYLE_CONTEXT.set(cellStyleMap);
    }

    public static void clear() {
        CELL_STYLE_CONTEXT.remove();
        HUSKY_CELL_STYLE_CONTEXT.remove();
    }

    @Override
    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {

        if(Objects.isNull(entity)){
            return super.getStyles(noneStyler, null);
        }

        if (!Objects.isNull(HUSKY_CELL_STYLE_CONTEXT.get())) {
            Map<String, HuskyCellStyle> huskyCellStyleMap = HUSKY_CELL_STYLE_CONTEXT.get();
            String key = entity.getName();
            if (huskyCellStyleMap.containsKey(key)) {
                Map<String, CellStyle> cellStyleMap = CELL_STYLE_CONTEXT.get();
                if (cellStyleMap == null) {
                    cellStyleMap = new HashMap<>(16);
                    CELL_STYLE_CONTEXT.set(cellStyleMap);
                }

                CellStyle style;

                if (!cellStyleMap.containsKey(key)) {
                    HuskyCellStyle cellStyle = huskyCellStyleMap.get(key);
                    style = workbook.createCellStyle();
                    // 赋值style属性
                    BeanUtils.copyProperties(cellStyle, style);
                    if (entity.isWrap()) {
                        style.setWrapText(true);
                    }
                    cellStyleMap.put(key, style);
                } else {
                    style = cellStyleMap.get(key);
                }
                return style;
            }
        }

        return super.getStyles(noneStyler, entity);

    }

}
