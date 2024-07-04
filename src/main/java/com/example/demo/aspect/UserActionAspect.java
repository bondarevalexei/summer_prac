package com.example.demo.aspect;

import com.example.demo.events.UserActionEvent;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserActionAspect {


    private final ApplicationEventPublisher eventPublisher;

    public UserActionAspect(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @After("execution(* com.example.demo.*.*(..))")
    public void logUserAction() {
        eventPublisher.publishEvent(new UserActionEvent(this, "User action performed"));
    }
}