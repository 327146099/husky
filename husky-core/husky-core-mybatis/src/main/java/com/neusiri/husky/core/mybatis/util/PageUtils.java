package com.neusiri.husky.core.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neusiri.husky.core.common.pagination.PageInfo;
import com.neusiri.husky.core.common.restful.request.param.PagingQueryParam;
import com.neusiri.husky.core.mybatis.paging.PagingHolder;
import com.neusiri.husky.core.util.bean.BeanUtils;
import com.neusiri.husky.core.util.biz.BizContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>分页工具类</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
public class PageUtils {

    public static class AutoPage<T> extends Page<T> {
    }

    /**
     * IPage实体转换PageInfo
     *
     * @param page IPage实体
     * @param <T>  查询的实体类型
     * @return PageInfo
     */
    public static <T> PageInfo<T> convertToPageInfo(IPage<T> page) {
        if (page == null) {
            return PageInfo.emptyPageInfo();
        }
        return PageInfo.<T>builder().current(page.getCurrent())
                .size(page.getSize())
                .total(page.getTotal())
                .pages(page.getPages())
                .records(page.getRecords()).build();
    }

    /**
     * IPage实体转换PageInfo
     *
     * @param page  IPage实体
     * @param <T>   查询的实体类型
     * @param clazz 转换的目标类型
     * @return PageInfo
     */
    public static <T, E> PageInfo<E> convertToPageInfo(IPage<T> page, Class<E> clazz) {
        if (page == null) {
            return PageInfo.emptyPageInfo();
        }
        List<T> records = page.getRecords();
        List<E> convertList = null;
        if (CollectionUtils.isNotEmpty(records)) {
            convertList = new ArrayList<>(records.size());
            for (T record : page.getRecords()) {
                E bean = BeanUtils.copy(record, clazz);
                convertList.add(bean);
            }
        } else {
            convertList = Collections.emptyList();
        }

        return PageInfo.<E>builder().current(page.getCurrent())
                .size(page.getSize())
                .total(page.getTotal())
                .pages(page.getPages())
                .records(convertList).build();
    }

    /**
     * 从查询条件获取分页信息
     *
     * @param queryParam 查询条件
     * @param <T>        实体类型
     * @return 分页参数
     */
    public static <T> IPage<T> getPageInfo(PagingQueryParam queryParam) {
        boolean isNull = true;
        int pageNum = 0;
        int pageSize = 10;
        if (queryParam.getPageNum() != null) {
            pageNum = queryParam.getPageNum();
            isNull = false;
        }
        if (queryParam.getPageSize() != null) {
            pageSize = queryParam.getPageSize();
            isNull = false;
        }

        if (isNull) {
            log.warn("[{}], 分页参数为空,请检查传递的参数", BizContextUtils.getBizId());
        }

        return new Page<>(pageNum, pageSize);
    }

    public static <T> IPage<T> getPageInfo() {
        long pageNum = NumberUtils.toLong(PagingHolder.getPageNum(), 0L);
        int pageSize = NumberUtils.toInt(PagingHolder.getPageSize(), 0);
        return new Page<>(pageNum, pageSize);
    }

    public static boolean isPage() {
        return Boolean.TRUE.equals(PagingHolder.getPagingFlag());
    }

    public static <T> IPage<T> getAutoPage() {
        return new AutoPage<T>();
    }

    public static boolean isAutoPage(Object params) {
        return params instanceof AutoPage;
    }

}
