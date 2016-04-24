<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
      <div class="am-header-left am-header-nav">
      	<c:if test="${back_url!=null && back_url!=''}">
	      	 <a href="${back_url }" class="">
	                <i class="am-header-icon am-icon-chevron-left" style="font-size: 25px"></i>
	          </a>
      	</c:if>
      	<c:if test="${back_url==null || back_url==''}">
	      	 <a href="${ctx }/mobile/index" class="">
	                <i class="am-header-icon am-icon-home" style="font-size: 25px"></i>
	          </a>
      	</c:if>
      </div>

      <h1 class="am-header-title">
          <a href="javascript:void(0)" class="">
            ${module_title }
          </a>
      </h1>

      <div class="am-header-right am-header-nav">
          <a href="${ctx }/mobile/feedback/site" class="">
                <i class="am-header-icon am-icon-question" style="font-size: 25px"></i>
          </a>
      </div>
  </header>