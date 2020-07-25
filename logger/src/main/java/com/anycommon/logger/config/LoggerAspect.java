package com.anycommon.logger.config;

import com.alibaba.fastjson.JSON;
import com.anycommon.logger.annotation.CommonLogger;
import com.anycommon.logger.dp.PlatformBusinessLogDP;
import com.anycommon.logger.service.LoggerService;
import com.anycommon.logger.util.CookieUtils;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.facade.PlatformSsoServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LoggerAspect {

    private final static Logger log = LoggerFactory.getLogger(LoggerAspect.class);

    @Resource
    private LoggerService loggerService;

    @Resource
    private PlatformSsoServiceFacade platformSsoServiceFacade;

    @Resource
    private LoggerConfig loggerConfig;

    /**
     * 对指定注解，进行横切，创建一个横切的对象方法
     */
    @Pointcut("@annotation(com.anycommon.logger.annotation.CommonLogger)")
    public void annotationPoint() {
    }


    /**
     * 对横切方法，进行反射处理，对使用了注解方法“前”：不仅可以捕捉到注解内容，还有方法名等，
     * 此处的作用主要是：可以对使用注解使用的方法，进行方法特殊逻辑处理（可以具体到哪个方法使用了哪个注解内容进行特殊处理）
     */
    @Before("annotationPoint()")
    public void beforeAnnotation(JoinPoint joinPoint) {
        PlatformBusinessLogDP platformBusinessLogDP = new PlatformBusinessLogDP();

        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            RpcResult<PlatformSysUserDP> platformsessionid = platformSsoServiceFacade.queryCurrent(CookieUtils.getValue(request, "PLATFORMSESSIONID"));
            platformBusinessLogDP.setCreator(platformsessionid.getData().getUserId());
            platformBusinessLogDP.setModifier(platformsessionid.getData().getUserId());
        } catch (Exception e) {
            log.error("e", e);
            platformBusinessLogDP.setCreator("system");
            platformBusinessLogDP.setModifier("system");
        }

        try {

            platformBusinessLogDP.setOrgCode(loggerConfig.getOrgCode());
            platformBusinessLogDP.setAppName(loggerConfig.getAppName());
            platformBusinessLogDP.setAppId(loggerConfig.getAppId());
            platformBusinessLogDP.setGmtCreate(new Date());
            platformBusinessLogDP.setGmtModified(new Date());

            Object[] args = joinPoint.getArgs();
            StringBuilder params = new StringBuilder();
            for (Object arg : args) {
                String param = "";
                try {
                    param = JSON.toJSONString(arg);
                } catch (Exception e) {
                    log.error("e", e);
                }
                params.append(",");
                params.append(param);

            }
            platformBusinessLogDP.setPreParams(params.toString());


            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            CommonLogger commonLogger = method.getAnnotation(CommonLogger.class);
            if (commonLogger != null) {
                platformBusinessLogDP.setOperatorMethodName(commonLogger.name());
            }
            loggerService.logger(platformBusinessLogDP);
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
