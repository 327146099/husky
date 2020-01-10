package com.neusiri.husky.core.extension.log.service;

import com.neusiri.husky.core.extension.log.entity.OperationLog;

/**
 * <p>操作日志service接口</p>
 * <p>创建日期：2019-09-06</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public interface IOperationLogService {

    /**
     * 保存日志信息
     *
     * @param operationLog 操作日志信息
     */
    void saveOperateLog(OperationLog operationLog);


}
