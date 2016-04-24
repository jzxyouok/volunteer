package com.comiyun.core.util;

import com.comiyun.core.sequence.IdWorker;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Properties;

/**
 * 系统工具类
 *
 * @author ydwcn
 * @ClassName: AppUtil
 * @date 2014-6-16 下午6:30:30
 */
public class AppUtil implements ApplicationContextAware {

    private static final String APP_PROPERTY = "appProps";

    private static ApplicationContext applicationContext;
    private static ServletContext servletContext;

    public static void init(ServletContext _servletContext) {
        servletContext = _servletContext;
    }

    public void setApplicationContext(ApplicationContext contex)
            throws BeansException {
        applicationContext = contex;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static ServletContext getServletContext() throws Exception {
        return servletContext;
    }

    public static String getAppAbsolutePath() {
        return servletContext.getRealPath("/");
    }

    public static String getRealPath(String path) {
        return servletContext.getRealPath(path);
    }

    public static String getClasspath() {
        String classPath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath();
        String rootPath = "";

        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(1);
            rootPath = rootPath.replace("/", "\\");
        }

        if ("/".equals(File.separator)) {
            rootPath = classPath.substring(1);
            rootPath = rootPath.replace("\\", "/");
        }
        return rootPath;
    }

    public static <T> T getBean(Class<T> cls) {
        return applicationContext.getBean(cls);
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    /**
     * @param key
     * @param @return
     * @return String
     * @throws
     * @Title: getProperty
     */
    public static String getConfigString(String key) {
        Properties pro = (Properties) getBean(APP_PROPERTY);
        Object o = pro.get(key);
        String v = null;
        if (o != null) {
            v = String.valueOf(o);
        }
        return v;
    }

    /**
     * @param key
     * @param @return
     * @return String
     * @throws
     * @Title: getProperty
     */
    public static Boolean getConfigBoolean(String key) {
        Properties pro = (Properties) getBean(APP_PROPERTY);
        Object o = pro.get(key);
        Boolean v = null;
        if (o != null) {
            v = Boolean.valueOf(String.valueOf(o));
        }
        return v;
    }

    /**
     * 系统 生成主键
     *
     * @param
     * @return long
     * @throws
     * @Title: generateId
     */
    public static long generateId() {
        long id = 0L;
        try {
            IdWorker idworker = null;
            idworker = (IdWorker) getBean("idWorker");
            id = idworker.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

}
