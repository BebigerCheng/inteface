package com.siy.protal.aop;

import com.siy.protal.response.GeneticResp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAop {
    
        /*private final Logger logger = LoggerFactory.getLogger("统一异常处理加载了");
        // 切入点表达式按需配置
        @Pointcut("execution(public * com.siy.protal.controller.*.*(..))")
        private void myPointcut() {
        }*/

        /*@Before("myPointcut()")
        public void before(JoinPoint joinPoint) {
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            logger.warn(className + "的" + methodName + "执行了");
            Object[] args = joinPoint.getArgs();
            StringBuilder log = new StringBuilder("入参为");
            for (Object arg : args) {
                log.append(arg + " ");
            }
            logger.warn(log.toString());
        }

        @AfterReturning(value = "myPointcut()", returning = "returnVal")
        public void afterReturin(Object returnVal) {
            logger.warn("方法正常结束了,方法的返回值:" + returnVal);
        }*/

        /*@AfterThrowing(value = "StationCardServiceAspect.myPointcut()", throwing = "e")
        public void afterThrowing(Throwable e) {
            if (e instanceof StationErrorCodeException) {
                logger.error("通知中发现异常StationErrorCodeException", e);
            } else {
                logger.error("通知中发现未知异常", e);
            }
        }*/

       /* @Around(value = "myPointcut()")
        public GeneticResp around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            logger.warn("前置增强...");
            Object result = null;
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Exception e) {
                logger.error(e.toString()+"--------------------------错误信息");
                GeneticResp<Object> objectGeneticResp = new GeneticResp<>();
                logger.info("#############程亮#########**********************响应结束**********************########帅气#############");
                return objectGeneticResp.error(-1000,"服务器忙");
            }
            return (GeneticResp)result;
        }*/
}
