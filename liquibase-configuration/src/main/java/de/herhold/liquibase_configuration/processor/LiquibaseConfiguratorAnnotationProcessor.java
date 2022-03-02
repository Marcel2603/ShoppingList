package de.herhold.liquibase_configuration.processor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LiquibaseConfiguratorAnnotationProcessor {

    @Around("@annotation(Traceable)")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Testoutput");
        Object result = joinPoint.proceed();
        System.out.println(result);
        return result;
    }
}
