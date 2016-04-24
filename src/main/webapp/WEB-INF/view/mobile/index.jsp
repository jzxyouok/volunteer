<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>
<section class="amz-features">
	<div class="am-g am-g-fixed" style="padding-top: 25px">
	  <div class="am-u-sm-6" style="text-align: center;">
		  <a href="${ctx }/mobile/act/list">
		  	<img src="/images/index_act.png">
		  	<h3 style="margin-top: 0px">最新活动</h3>
		  </a>
	  </div>
	  <div class="am-u-sm-6" style="text-align: center;">
		  <a href="${ctx }/mobile/ex/list">
		  	<img src="/images/index_ex.png" >
		  	<h3 style="margin-top: 0px">最新兑换</h3>
		  </a>
	  </div>
	</div>
	
	<div class="am-g am-g-fixed">
	  <div class="am-u-sm-6" style="text-align: center;">
	  	<a href="${ctx }/mobile/persion/umain">
		  	<img src="/images/index_persion.png">
	  		<h3 style="margin-top: 0px">我的信息</h3>
		</a>
	  </div>
	  <div class="am-u-sm-6" style="text-align: center;">
	  	<a href="${ctx }/mobile/ex/provider">
		  	<img src="/images/index_fankui.png">
	  		<h3 style="margin-top: 0px">爱心联盟</h3>
		</a>
	  </div>
	</div>
</section>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>
