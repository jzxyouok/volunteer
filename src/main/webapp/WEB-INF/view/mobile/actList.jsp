<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>

<c:forEach var="s" items="${actlist }" varStatus="status">
	<div class="am-panel am-panel-secondary">
	  <div class="am-panel-hd">
	  	<span class="am-icon-bookmark"> 
	  		<a href="${ctx }/mobile/act/actinfo?actId=${s.id}">${s.name } </a>
	  	</span>
	  	<span class="am-icon-user" style="float:right"> ${s.code }</span>
	  </div>
	  <div class="am-panel-bd">
	  	<figure data-am-widget="figure" class="am am-figure am-figure-default ">
      	  <img src="${ctx }/weixin/qrcode/get?id=${s.qrCodeId}" style="width: 150px;height: 150px"/>
          <figcaption class="am-figure-capition-btm">
            ${s.content }
          </figcaption>
  		</figure>
	  </div>
	  <p class="am-sans-serif">${s.content }</p>
	  <footer class="am-panel-footer">
	  	<div class="am-g am-g-collapse">
		  <div class="am-u-sm-7">
        	<span class="am-badge am-badge-warning am-round">人数上限:${s.persionNum }</span>
		  	<span class="am-badge am-badge-primary am-round">每人积分:${s.perIntegral }</span>
		  </div>
		  <span class="am-u-sm-3">
		  	 <a id="ex-btn" class="am-btn am-btn-danger am-btn-xs" style="float:right" href="${ctx }/mobile/act/actinfo?actId=${s.id}">
				  <i class="am-icon-shopping-cart">详情</i>
			 </a>
		  </span>
		</div>
	  </footer>
	</div>
</c:forEach>

<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>