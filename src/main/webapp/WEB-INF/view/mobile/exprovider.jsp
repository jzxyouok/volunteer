<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/raty/jquery.raty.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>
<c:forEach var="s" items="${list }" varStatus="status">
	<div class="am-panel am-panel-secondary">
	  <div class="am-panel-hd">
	  	<span class="am-icon-user">
	  	<a href="javascript:void(0)" class="am-list-item-hd ">${s.realname }</a>
	  	</span>
	  	<span style="float:right">
			
        </span>
	  </div>
	  <div class="am-panel-bd">
	    服务态度：
	    <div title="poor" style="width: 100px;">
				<c:if test="${s.providerTd==0}">
					<img src="/plugins/raty/img/star-off.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerTd==1}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerTd==2}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerTd==3}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerTd==4}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerTd>=5}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="5" title="poor">
				</c:if>
	  		</div>
	  	 服务效率：
	    <div title="poor" style="width: 100px;">
				<c:if test="${s.providerXl==0}">
					<img src="/plugins/raty/img/star-off.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerXl==1}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerXl==2}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerXl==3}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerXl==4}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-off.png" alt="5" title="poor">
				</c:if>
				<c:if test="${s.providerXl>=5}">
					<img src="/plugins/raty/img/star-on.png" alt="1" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="2" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="3" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="4" title="poor">
		  			<img src="/plugins/raty/img/star-on.png" alt="5" title="poor">
				</c:if>
	  	</div>	
	  	${s.remark }
	  </div>
	</div>
</c:forEach>

<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>