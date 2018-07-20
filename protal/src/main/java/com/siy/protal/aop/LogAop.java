package com.siy.protal.aop;


import com.siy.protal.exception.PcException;
import com.siy.protal.response.GeneticResp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

@Aspect
@Component
public class LogAop {
    private Logger logger = LoggerFactory.getLogger(LogAop.class);

    /**
     * 切面
     * 解释下：
     * 第一个 * 代表任意修饰符及任意返回值.
     * 第二个 * 任意包名
     * 第三个 * 代表任意方法.
     * 第四个 * 定义在web包或者子包
     * 第五个 * 任意方法
     * .. 匹配任意数量的参数.
     */

    @Pointcut("execution(public * com.siy.protal.controller.*.*(..))")
    public void webLog() {
    }

    /*@Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        logger.info("#############程亮#########**********************请求开始**********************#########帅气#############");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        //获取所有参数方法一：
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            System.out.println(paraName + ": " + request.getParameter(paraName));
        }
    }*/
    @Around(value = "webLog()")
    public GeneticResp around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        logger.info("#############程亮#########**********************请求开始**********************#########帅气#############");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(proceedingJoinPoint.getArgs()));
        //获取所有参数方法一：
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            System.out.println(paraName + ": " + request.getParameter(paraName));
        }
        Object result = null;
        Exception e = null;
        try {
            result = proceedingJoinPoint.proceed();
            // 处理完请求，返回内容
            logger.info("#############程亮#########**********************响应结束**********************########帅气#############");
        } catch (Exception ex) {
            logger.error(ex.toString()+"--------------------------错误信息");
            GeneticResp<Object> objectGeneticResp = new GeneticResp<>();
            e = ex;
            logger.info("#############程亮#########**********************响应结束**********************########帅气#############");
            return objectGeneticResp.error(-1000,"服务器忙");
        } finally {
            if(e != null){
                throw new PcException(e);
            }
        }
        return (GeneticResp)result;
    }


    /*@AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        // 处理完请求，返回内容
        logger.info("#############程亮#########**********************响应结束**********************########帅气#############");
    }*/
}
