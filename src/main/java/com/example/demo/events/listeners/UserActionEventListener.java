package com.example.demo.events.listeners;

import com.example.demo.events.UserActionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserActionEventListener {

    @EventListener
    public void handleUserActionEvent(UserActionEvent event) {
        System.out.println("Received UserActionEvent - " + event.getMessage());
    }
}