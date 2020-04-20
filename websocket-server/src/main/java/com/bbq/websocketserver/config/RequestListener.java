package com.bbq.websocketserver.config;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author liutf
 * @date 2020-04-18
 */
@WebListener
@Component
public class RequestListener implements ServletRequestListener {

    public RequestListener(){

    }
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 将所有http请求都携带httpSession
        ((HttpServletRequest)sre.getServletRequest()).getSession();
    }
}
