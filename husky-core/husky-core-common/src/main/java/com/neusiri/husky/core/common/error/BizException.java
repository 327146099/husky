package com.neusiri.husky.core.common.error;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>业务异常</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Data
@Accessors
public class BizException extends BaseException {

    /**
     * 业务异常代码
     */
    @ApiModelProperty("业务异常编码")
    private Integer bizCode;

    /**
     * 视图展示异常信息
     */
    private String viewErrMsg;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BizException(Integer bizCode, String message) {
        super(message);
        this.bizCode = bizCode;
    }
}
