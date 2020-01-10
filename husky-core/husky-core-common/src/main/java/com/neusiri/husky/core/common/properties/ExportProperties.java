package com.neusiri.husky.core.common.properties;

import lombok.Data;

/**
 * <p>导出属性配置</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class ExportProperties {

    /**
     * 是否检查最新模板(禁用模板缓存,正式环境请关闭)
     */
    private Boolean autoCheck;

    /**
     * pdf导出属性
     */
    private PdfExportProperties pdf;
}
