package com.comiyun.core.web.listener;

import com.comiyun.core.util.AppUtil;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * 重写Spring监听器
 *
 * @author ydwcn
 * @ClassName: StartupListener
 * @date 2014-6-17 下午5:36:17
 */
public class StartupListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        AppUtil.init(event.getServletContext());
    }

}
