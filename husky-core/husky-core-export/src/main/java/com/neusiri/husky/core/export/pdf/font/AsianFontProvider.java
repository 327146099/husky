package com.neusiri.husky.core.export.pdf.font;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>xwork字体Provider,解决中文无法显示问题</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
public class AsianFontProvider extends XMLWorkerFontProvider {

    @Override
    public Font getFont(final String fontName, String encoding, float size, final int style) {

        if (size == 0) {
            size = 4;
        }
        BaseFont baseFont = null;
        Font font = null;
        try {
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            font = new Font(baseFont, size, style);
        } catch (Exception e) {
            log.error("字体文件初始化失败", e);
        }
        if (baseFont == null) {
            font = super.getFont("STSong-Light", "UniGB-UCS2-H", size, style);
        }

        return font;

    }

}
