package com.comiyun.core.web.servlet;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringMvcServlet extends DispatcherServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void noHandlerFound(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        String requestURI = request.getRequestURI();
        this.logger.debug("not foud handle mapping for url: " + requestURI);

        String contextPath = request.getContextPath();

        requestURI = requestURI.replace(".do", "");
        int cxtIndex = requestURI.indexOf(contextPath);
        if (cxtIndex != -1) {
            requestURI = requestURI.substring(cxtIndex + contextPath.length());
        }
        String[] paths = requestURI.split("[/]");
        String jspPath = null;
        if ((paths != null) && (paths.length == 5))
            jspPath = "/" + paths[1] + "/" + paths[2] + "/" + paths[3] + paths[4] + ".jsp";
        else {
            jspPath = requestURI + ".jsp";
        }
        this.logger.debug("requestURI:" + request.getRequestURI() + " and forward to /" + jspPath);
        request.getRequestDispatcher("/" + jspPath).forward(request, response);
    }


}
