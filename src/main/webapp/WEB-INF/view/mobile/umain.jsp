<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>
	<div style="background-image:url(${ctx}/images/mp_bg.png);width: 100%;height: 205px;background-size:cover;">
	   <div style="display:inline-block;">
	   		<img src="${ctx}/weixin/qrcode/get?id=${u.qrCodeId}" style="width: 100px;height: 100px;margin: 30px"/>
	   </div>
       <div style="display:inline-block;font-size: 18px;font-weight: bold;color:#EBFAF8">
	       	 ${u.name }<br/>
	       	 <c:if test="${u.familyId!=null }">
	       	 	${u.familyName }<br/>
	       	 </c:if>
       </div>
	</div>
<!-- <img src="${ctx}/images/mp_bg.png" style="width: 100%;height: 100%"/> -->
<!--  	<img src="${ctx}/weixin/qrcode/get?id=432105202875764736" style="width: 100px;height: 100px"/> -->
	<div id="widget-list">
		<ul class="am-list m-widget-list" style="transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1); 
		transition-duration: 0ms; transform: translate(0px, 0px) translateZ(0px);">
			
			<li><a href="${ctx }/mobile/persion/uinfo" data-rel="divider">
				&nbsp;<img class="widget-icon" src="${ctx }/images/grxx.png" alt="基本信息">
				&nbsp;&nbsp;
				<span class="widget-name">基本信息</span>
			</a></li>
			
			<li><a href="${ctx }/mobile/family/info" data-rel="divider">
				&nbsp;<img class="widget-icon" src="${ctx }/images/family_48.png" alt="基本信息">
				&nbsp;&nbsp;
				<span class="widget-name">家庭信息</span>
			</a></li>
			
			<li><a href="${ctx }/mobile/persion/wdhd" data-rel="divider">
				&nbsp;<img class="widget-icon" src="${ctx }/images/wdhd.png" alt="我的活动">
				&nbsp;&nbsp;
				<span class="widget-name">我的活动</span>
			</a></li>
			
			<li><a href="${ctx }/mobile/persion/wddh" data-rel="divider">
				&nbsp;<img class="widget-icon" src="${ctx }/images/wddh.png" alt="我的兑换">
				&nbsp;&nbsp;
				<span class="widget-name">我的兑换</span>
			</a></li>
			<li><a href="${ctx }/mobile/persion/jfmx" data-rel="divider">
				&nbsp;<img class="widget-icon" src="${ctx }/images/jfmx.png" alt="积分明细">
				&nbsp;&nbsp;
				<span class="widget-name">我的积分</span>
			</a></li>
<!-- 			<li><a href="javascript:void(0)" data-rel="divider"> -->
<!-- 				&nbsp;<img class="widget-icon" src="${ctx }/images/qrcode.png" alt="积分明细"> -->
<!-- 				&nbsp;&nbsp; -->
<!-- 				<span class="widget-name">扫一扫</span> -->
<!-- 			</a></li> -->
		</ul>
	</div>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>
