package com.neusiri.husky.core.auth.entity;

import com.neusiri.husky.core.common.restful.model.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>系统用户信息</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
public class UserInfo extends BaseEntity {

    @ApiModelProperty("登录账号")
    private String account;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("部门信息")
    private Dept dept;

    @ApiModelProperty("角色信息")
    private List<String> roles;

    @ApiModelProperty("权限信息")
    private List<String> authorities;

    @ApiModelProperty("数据权限")
    private List<SysDataScope> dataScopes;

    @ApiModelProperty("是否是管理员")
    private boolean isAdmin;

}
