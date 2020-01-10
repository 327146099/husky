package com.neusiri.husky.core.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.neusiri.husky.core.auth.entity.Dept;
import com.neusiri.husky.core.auth.util.AuthUtils;
import com.neusiri.husky.core.common.restful.model.AbstractBizEntity;
import com.neusiri.husky.core.util.biz.BizContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>BizEntity信息自动填充</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Component
@Slf4j
public class BizEntityObjectHandler implements MetaObjectHandler {

    /**
     * 添加时更新
     *
     * @param metaObject 参数信息
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        // 设置数据权限
        if (AuthUtils.getCurrentUser() != null && AuthUtils.getCurrentUser().getDept() != null && AuthUtils.getCurrentUser().getDept().getId() != null) {
            Dept dept = AuthUtils.getCurrentUser().getDept();
            this.setFieldValByName(AbstractBizEntity.Fields.dataPermission.name(), String.valueOf(dept.getId()), metaObject);
        }

        Object originalObject = metaObject.getOriginalObject();

        if (!(originalObject instanceof AbstractBizEntity)) {
            log.warn("持久化的实体请继承 com.neusiri.husky.core.common.restful.model.AbstractBizEntity 后使用");
        }

        // 获取操作时间
        Date operateTime = BizContextUtils.getOperateTime();

        // 设置是否删除
        this.setFieldValByName(AbstractBizEntity.Fields.deleted.name(), "0", metaObject);
        // 设置版本号
        this.setFieldValByName(AbstractBizEntity.Fields.version.name(), 1, metaObject);

        // 设置创建人和创建时间
        this.setFieldValByName(AbstractBizEntity.Fields.gmtCreate.name(), operateTime, metaObject);
        this.setFieldValByName(AbstractBizEntity.Fields.createBy.name(), String.valueOf(AuthUtils.getCurrentUser().getId()), metaObject);

        // 设置更新人和更新时间
        this.setFieldValByName(AbstractBizEntity.Fields.gmtModified.name(), operateTime, metaObject);
        this.setFieldValByName(AbstractBizEntity.Fields.modifiedBy.name(), String.valueOf(AuthUtils.getCurrentUser().getId()), metaObject);
    }

    /**
     * 修改时更新
     *
     * @param metaObject 参数信息
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        // 获取操作时间
        Date operateTime = BizContextUtils.getOperateTime();

        // 设置更新人和更新时间
        this.setFieldValByName(AbstractBizEntity.Fields.gmtModified.name(), operateTime, metaObject);
        this.setFieldValByName(AbstractBizEntity.Fields.modifiedBy.name(), String.valueOf(AuthUtils.getCurrentUser().getId()), metaObject);
    }

}
