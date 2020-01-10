package com.neusiri.husky.core.common.properties;

import lombok.Data;

/**
 * <p>静态资源映射</p>
 * <p>创建日期：2019-08-23</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class ResourceProperty {

    /**
     * 访问地址
     */
    private String path;

    /**
     * 本地地址
     */
    private String location;

}
