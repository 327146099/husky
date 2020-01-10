package com.neusiri.husky.core.extension.log.aspect;

import com.alibaba.fastjson.JSONObject;
import com.neusiri.husky.core.auth.entity.UserInfo;
import com.neusiri.husky.core.auth.util.AuthUtils;
import com.neusiri.husky.core.common.annotation.Log;
import com.neusiri.husky.core.common.dict.constant.BusinessStatus;
import com.neusiri.husky.core.extension.log.entity.OperationLog;
import com.neusiri.husky.core.extension.log.event.LogPersistEvent;
import com.neusiri.husky.core.util.network.IpUtils;
import com.neusiri.husky.core.util.network.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>操作日志记录处理</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.neusiri.husky.core.common.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    /**
     * 处理日志信息
     *
     * @param joinPoint 切点
     * @param exception 切点方法抛出的异常信息
     */
    private void handleLog(final JoinPoint joinPoint, final Exception exception) {
        // 获得注解
        Log controllerLog = getAnnotationLog(joinPoint);
        if (controllerLog == null) {
            return;
        }

        // 获取当前的用户
        UserInfo currentUser = AuthUtils.getCurrentUser();
        // *========数据库日志=========*//
        OperationLog operationLog = new OperationLog();
        operationLog.setOperateTime(new Date());
        operationLog.setStatus(BusinessStatus.SUCCESS.getValue());

        HttpServletRequest request = RequestUtils.getRequest();
        String ipAddress = IpUtils.getIpAddr(request);

        // 请求的地址
        operationLog.setOperateIp(ipAddress);
        //请求的服务地址
        operationLog.setOperateUrl(request.getRequestURI());

        // 如果用户不为空,设置用户名称,部门等信息
        if (!Objects.isNull(currentUser)) {
            operationLog.setOperatorName(currentUser.getAccount());
            if (!Objects.isNull(currentUser.getDept()) && StringUtils.isNotEmpty(currentUser.getDept().getDeptName())) {
                operationLog.setDeptName(currentUser.getDept().getDeptName());
            }
        }
        // 如果有异常信息,设置操作为失败状态
        if (exception != null) {
            operationLog.setStatus(BusinessStatus.FAIL.ordinal());
            operationLog.setErrorMsg(StringUtils.substring(exception.getMessage(), 0, 2000));
        }
        // 设置方法名称
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operationLog.setMethod(className + "." + methodName + "()");

        //获取请求参数
        Object[] args = joinPoint.getArgs();
        // 设置请求方式
        operationLog.setRequestMethod(request.getMethod());

        List<Object> params = new ArrayList<>();
        // 去除不能序列化的参数和多媒体文件
        for (Object arg : args) {
            if (arg instanceof Serializable && !(arg instanceof MultipartFile)) {
                params.add(arg);
            }
        }
        // 处理设置注解上的参数
        getControllerMethodDescription(controllerLog, operationLog, params);

        // 持久化日志信息
        persistOperaLog(operationLog);
    }

    /**
     * 持久化日志信息
     *
     * @param operationLog 操作日志信息
     */
    private void persistOperaLog(OperationLog operationLog) {

        applicationContext.publishEvent(new LogPersistEvent(operationLog));

    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operaLog 日志参数
     */
    private void setRequestValue(OperationLog operaLog, List<Object> args) {

        String params = JSONObject.toJSONString(args);
        operaLog.setOperateParam(StringUtils.substring(params, 0, 2000));
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log      日志注解
     * @param operaLog 操作日志
     */
    private void getControllerMethodDescription(Log log, OperationLog operaLog, List<Object> args) {
        // 设置action动作
        operaLog.setBusinessType(log.businessType().getValue());
        // 设置标题
        operaLog.setTitle(log.title());
        // 设置操作人类别
        operaLog.setOperatorType(log.operatorType().getValue());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operaLog, args);
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

}
