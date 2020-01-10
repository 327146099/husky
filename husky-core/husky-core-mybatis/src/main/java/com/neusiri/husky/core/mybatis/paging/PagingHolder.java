package com.neusiri.husky.core.mybatis.paging;

/**
 * <p>分页信息上下文环境</p>
 * <p>创建日期：2019-08-14</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class PagingHolder {

    private static ThreadLocal<String> pageSize = new ThreadLocal<>();

    private static ThreadLocal<String> pageNum = new ThreadLocal<>();

    private static ThreadLocal<Boolean> pagingFlag = new ThreadLocal<>();

    public static String getPageSize() {
        return pageSize.get();
    }

    public static void setPageSize(String pageSize) {
        PagingHolder.pageSize.set(pageSize);
    }

    public static String getPageNum() {
        return pageNum.get();
    }

    public static void setPageNum(String pageNum) {
        PagingHolder.pageNum.set(pageNum);
    }

    public static void clear() {
        PagingHolder.pageSize.remove();
        PagingHolder.pageNum.remove();
        PagingHolder.pagingFlag.remove();
    }

    public static Boolean getPagingFlag() {
        return PagingHolder.pagingFlag.get();
    }

    public static void setPagingFlag(Boolean pagingFlag) {
        PagingHolder.pagingFlag.set(pagingFlag);
    }

}
