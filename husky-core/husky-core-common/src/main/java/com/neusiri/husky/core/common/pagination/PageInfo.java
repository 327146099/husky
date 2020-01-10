package com.neusiri.husky.core.common.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <p>分页信息</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Builder
@Getter
@ApiModel(description = "分页信息")
public class PageInfo<T> implements Serializable {

    /**
     * empty pageInfo
     */
    private static PageInfo EMPTY_PAGE_INFO = PageInfo.builder().records(Collections.emptyList()).total(0).size(0).current(1).pages(0).build();

    /**
     * 获取空pageInfo
     *
     * @param <T> PageInfo保存类型
     * @return 空pageInfo
     */
    @SuppressWarnings("unchecked")
    public static <T> PageInfo<T> emptyPageInfo() {
        return (PageInfo<T>) EMPTY_PAGE_INFO;
    }

    /**
     * 查询数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private List<T> records;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    private long total;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数")
    private long size;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long current;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private long pages;

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    @ApiModelProperty(value = "是否存在上一页")
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    @ApiModelProperty(value = "是否存在下一页")
    public boolean hasNext() {
        return this.current < this.pages;
    }


}
