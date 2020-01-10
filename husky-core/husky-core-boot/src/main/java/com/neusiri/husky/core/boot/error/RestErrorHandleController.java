package com.neusiri.husky.core.boot.error;


import com.neusiri.husky.core.common.error.BizException;
import com.neusiri.husky.core.common.error.ServiceException;
import com.neusiri.husky.core.common.restful.response.AppResponse;
import com.neusiri.husky.core.common.restful.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * <p>异常全局处理器</p>
 * <p>创建日期：2019-08-13</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@RestControllerAdvice
@Slf4j
public class RestErrorHandleController {

    /**
     * 访问权限异常
     */
    private static final String ACCESS_DENIED_EXCEPTION = "org.springframework.security.access.AccessDeniedException";

    /**
     * 参数校验处理
     *
     * @param e 参数校验异常
     * @return 返回json参数
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public AppResponse serviceExceptionHandler(ConstraintViolationException e) {

        log.warn("参数错误", e);

        Optional<ConstraintViolation<?>> first = e.getConstraintViolations().stream().findFirst();
        String message;
        if (first.isPresent()) {
            // 获取参数错误实体
            ConstraintViolation<?> constraintViolation = first.get();
            // 获取参数错误信息
            message = constraintViolation.getMessage();
            // 将错误信息包装返回
        } else {
            message = "参数错误,请检查后重新提交";
        }

        return AppResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), message);

    }

    /**
     * 参数校验处理
     *
     * @param e 绑定异常
     * @return 返回json参数
     */
    @ExceptionHandler({BindException.class})
    public AppResponse bindExceptionHandler(BindException e) {

        log.warn("参数错误", e);

        Optional<ObjectError> first = e.getAllErrors().stream().findFirst();

        String message;
        if (first.isPresent()) {
            // 封装对象错误
            ObjectError objectError = first.get();
            // 获取错误信息
            message = objectError.getDefaultMessage();
        } else {
            message = "参数错误,请检查后重新提交";
        }

        // 将错误信息包装返回
        return AppResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), message);
    }

    /**
     * 方法参数错误校验处理
     *
     * @param e 方法参数校验异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public AppResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.warn("参数错误", e);
        // 封装对象错误
        BindingResult bindingResult = e.getBindingResult();

        Optional<ObjectError> first = bindingResult.getAllErrors().stream().findFirst();
        String message;
        if (first.isPresent()) {
            // 获取错误信息
            message = first.get().getDefaultMessage();
        } else {
            message = "参数错误,请检查后重新提交";
        }

        // 将错误信息包装返回
        return AppResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), message);
    }

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return 返回json参数
     */
    @ExceptionHandler({BizException.class})
    public AppResponse bizExceptionHandler(BizException e) {

        log.error("业务异常", e);
        // 将错误信息包装返回
        String msg = e.getViewErrMsg() != null ? e.getViewErrMsg() : "业务异常,请重试";
        int code = e.getBizCode() != null ? e.getBizCode() : ResponseStatus.BIZ_ERROR.getCode();
        return AppResponse.fail(code, msg);
    }

    /**
     * 服务异常处理
     *
     * @param e 服务异常
     * @return 返回json参数
     */
    @ExceptionHandler({ServiceException.class})
    public AppResponse serviceExceptionHandler(ServiceException e) {

        log.error("服务异常", e);
        // 将错误信息包装返回
        String msg = e.getViewErrMsg() != null ? e.getViewErrMsg() : "服务异常,请重试";
        return AppResponse.fail(ResponseStatus.SERVICE_ERROR.getCode(), msg);
    }

    /**
     * 请求类型不支持异常
     *
     * @param e 请求类型不支持
     * @return 错误结果
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public AppResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("请求类型不支持", e);
        return AppResponse.fail("当前的请求类型不支持,请检查后重试");
    }

    /**
     * 上传的文件大小超过限制
     *
     * @param e 上传的文件大小超过限制
     * @return 错误结果
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public AppResponse maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("上传的文件大小超过限制", e);
        return AppResponse.fail("您上传的文件大小超过限制，请检查后重试");
    }

    /**
     * 未知基类处理
     *
     * @param e Exception错误处理
     * @return 返回json参数
     */
    @ExceptionHandler({Exception.class})
    public AppResponse globalExceptionHandler(Exception e) {

        if (ACCESS_DENIED_EXCEPTION.equals(e.getClass().getName())) {

            log.error("访问权限错误", e);

            return AppResponse.unauthorized("您没有权限访问");
        }

        log.error("unknown error", e);
        // 将错误信息包装返回
        return AppResponse.fail("系统异常,请重试");
    }


}
