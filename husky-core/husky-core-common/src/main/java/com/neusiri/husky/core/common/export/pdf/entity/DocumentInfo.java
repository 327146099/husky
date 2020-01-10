package com.neusiri.husky.core.common.export.pdf.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>pdf文档信息</p>
 * <p>创建日期：2019-09-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class DocumentInfo implements Serializable {

    /**
     * pdf作者
     */
    private String author;

    /**
     * pdf创建者
     */
    private String creator;

    /**
     * pdf主题
     */
    private String subject;

    /**
     * pdf标题
     */
    private String title;
}
