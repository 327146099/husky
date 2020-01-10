package com.neusiri.husky.core.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>上传枚举类</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@AllArgsConstructor
@Getter
public enum UploadConstants {

    /**
     * 本地路径保存方式
     */
    PATH("path", "本地路径保存方式"),

    /**
     * FTP服务器保存方式
     */
    FTP("ftp", "FTP服务器保存方式");

    private String type;

    private String description;


}
