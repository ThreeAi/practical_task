package com.practical.task.configurations;

import com.practical.task.models.Log;
import com.practical.task.repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LogRepository logRepository;

    @Before("@annotation(audit) && execution(* *(..))")
    public void beforeAudit(JoinPoint joinPoint, Audit audit) {
        String methodName = joinPoint.getSignature().getName();
        String username = getUsername();

        Log log = new Log();
        log.setMethod(methodName);
        log.setUserName(username);
        logRepository.save(log);
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "Anonymous";
    }
}
