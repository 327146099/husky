package com.neusiri.husky.core.common.dict.constant;

import com.neusiri.husky.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>业务操作类型</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@AllArgsConstructor
@Getter
public enum BusinessType implements BaseEnum<Integer> {

    /**
     * 其它
     */
    OTHER(99, "其它"),

    /**
     * 查询
     */
    QUERY(0, "查询"),

    /**
     * 新增
     */
    INSERT(1, "新增"),

    /**
     * 修改
     */
    UPDATE(2, "修改"),

    /**
     * 删除
     */
    DELETE(3, "删除"),

    /**
     * 授权
     */
    GRANT(4, "授权"),

    /**
     * 导出
     */
    EXPORT(6, "导出"),

    /**
     * 导入
     */
    IMPORT(7, "导入"),

    /**
     * 强退
     */
    FORCE(8, "强退"),

    /**
     * 生成代码
     */
    GENERATE_CODE(9, "生成代码"),

    /**
     * 清空数据
     */
    CLEAN(10, "清空数据");

    private final Integer value;

    private final String name;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }


}
