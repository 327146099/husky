package com.neusiri.husky.core.export.pdf.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.neusiri.husky.core.common.error.ServiceException;
import com.neusiri.husky.core.common.export.constant.HuskyPDFTemplateConstants;
import com.neusiri.husky.core.common.export.pdf.entity.DocumentInfo;
import com.neusiri.husky.core.export.pdf.font.AsianFontProvider;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>pdf视图解析器</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Controller(HuskyPDFTemplateConstants.HUSKY_PDF_TEMPLATE_VIEW)
@Slf4j
public class HuskyPdfTemplateView extends AbstractView {

    @Autowired
    private GroupTemplate groupTemplate;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 读取模板
        String path = model.get(HuskyPDFTemplateConstants.PATH).toString();
        String content = getTemplate(path, model);
        // 创建文档
        Document document = new Document(PageSize.A4);
        @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        document.open();

        // 设置文档信息
        Object documentInfoObj = model.get(HuskyPDFTemplateConstants.DOCUMENT_INFO);
        setDocumentInfo(document, documentInfoObj);

        try {
            //生成文档
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), null, StandardCharsets.UTF_8, new AsianFontProvider());
        } finally {
            //关闭生成的文档
            document.close();
        }
        //获取生成文档的字节码
        byte[] bytes = outputStream.toByteArray();

        //设置response响应参数
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        String fileName = model.getOrDefault(HuskyPDFTemplateConstants.EXPORT_NAME, "exportPdf").toString();
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".pdf");

        @Cleanup InputStream is = new ByteArrayInputStream(bytes);
        @Cleanup OutputStream os = response.getOutputStream();
        //将文档内容写入输出流
        IOUtils.copy(is, os);

    }

    /**
     * 获取模板
     *
     * @param path  模板路径
     * @param model 模板绑定参数
     * @return pdf模板内容
     */
    private String getTemplate(String path, Map<String, Object> model) {
        if (StringUtils.isEmpty(path)) {
            throw new ServiceException("template path is empty");
        }
        Template template = groupTemplate.getTemplate(path);
        template.fastBinding(model);
        return template.render();
    }

    /**
     * 设置文档信息
     *
     * @param document        文档对象
     * @param documentInfoObj 文档基本信息
     */
    private void setDocumentInfo(Document document, Object documentInfoObj) {
        if (documentInfoObj instanceof DocumentInfo) {
            DocumentInfo documentInfo = (DocumentInfo) documentInfoObj;
            //设置文档信息
            document.addAuthor(documentInfo.getAuthor());
            document.addCreator(documentInfo.getCreator());
            document.addSubject(documentInfo.getSubject());
            document.addCreationDate();
            document.addTitle(documentInfo.getTitle());

        } else {
            log.warn("文档信息对象类型错误,请使用 {}", DocumentInfo.class);
        }
    }

}
