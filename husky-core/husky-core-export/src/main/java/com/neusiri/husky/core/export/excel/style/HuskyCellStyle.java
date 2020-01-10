package com.neusiri.husky.core.export.excel.style;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * <p>自定义表格样式</p>
 * <p>创建日期：2019-09-27</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@Accessors(chain = true)
public class HuskyCellStyle {

    private BorderStyle borderLeft = BorderStyle.THIN;

    private BorderStyle borderRight = BorderStyle.THIN;

    private BorderStyle borderBottom = BorderStyle.THIN;

    private BorderStyle borderTop = BorderStyle.THIN;

    /**
     * 垂直对齐方式
     */
    private HorizontalAlignment alignment = HorizontalAlignment.CENTER;

    /**
     * 水平对齐方式
     */
    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;

    /**
     * 背景色
     */
    private short fillBackgroundColor;

    /**
     * 前景色
     */
    private short fillForegroundColor;

    /**
     * 填充方式
     */
    private FillPatternType fillPattern;

}
