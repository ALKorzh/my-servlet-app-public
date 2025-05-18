package com.karzhou.app.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

    private static final Logger logger = LogManager.getLogger(SessionAttributeListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        logger.info("Session attribute added: {} = {}", event.getName(), event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        logger.info("Session attribute removed: {}", event.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        Object newValue = event.getSession().getAttribute(event.getName());
        logger.info("Session attribute replaced: {} = {}", event.getName(), newValue);
    }
}
