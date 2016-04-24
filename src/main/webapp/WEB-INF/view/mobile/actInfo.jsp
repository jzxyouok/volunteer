<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: 'wx9f7d59caf6360c4e', // 必填，公众号的唯一标识
	    timestamp: 1447336908, // 必填，生成签名的时间戳
	    nonceStr: 'Wm3WZYTPz0wzccnW', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: [
			'scanQRCode'
		] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
		//活动报名
		$('#hdbm').click(function () {
			  var $btn = $(this);
			  $btn.button('loading');
			  $.post('${ctx}/mobile/act/actbm',{actId:$('#actId').val()},function(result){
				  $btn.button('reset');
	              alert(result.msg);
	          },'json');
			});
		
		//扫描签到
		$('#hdqd').click(function () {
			wx.scanQRCode({
			        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			        success: function (res) {
			        	var $btn = $('#hdqd');
						$btn.button('loading');
			        	var url = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			        	
			        	$.post('${ctx}/mobile/act/actqd',{qrCodeUrl:url},function(result){
			  			  $btn.button('reset');
			              alert(result.msg);
			            },'json');
			    	}
			});
		});
		
		//扫描签退
		$('#hdqt').click(function () {
			wx.scanQRCode({
			        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			        success: function (res) {
			        	var $btn = $('#hdqt');
						$btn.button('loading');
			        	var url = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			        	
			        	$.post('${ctx}/mobile/act/actqt',{qrCodeUrl:url},function(result){
			  			  $btn.button('reset');
			              alert(result.msg);
			            },'json');
			    	}
			});
		});
		
		//扫描代报
		$('#hddb').click(function () {
			wx.scanQRCode({
			        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			        success: function (res) {
			        	var $btn = $('#hddb');
						$btn.button('loading');
			        	var url = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			        	$.post('${ctx}/mobile/act/actdb',{actId:$('#actId').val(),qrCodeUrl:url},function(result){
			  			  $btn.button('reset');
			              alert(result.msg);
			            },'json');
			    	}
			});
		});
		
		//扫描代签到
		$('#hddq').click(function () {
			wx.scanQRCode({
			        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			        success: function (res) {
			        	var $btn = $('#hddq');
						$btn.button('loading');
			        	var url = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			        	$.post('${ctx}/mobile/act/actdq',{actId:$('#actId').val(),qrCodeUrl:url},function(result){
			  			  $btn.button('reset');
			              alert(result.msg);
			            },'json');
			    	}
			});
		});
		//扫描代签退
		$('#hddt').click(function () {
			wx.scanQRCode({
			        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			        success: function (res) {
			        	var $btn = $('#hddt');
						$btn.button('loading');
			        	var url = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			        	$.post('${ctx}/mobile/act/actdt',{actId:$('#actId').val(),qrCodeUrl:url},function(result){
			  			  $btn.button('reset');
			              alert(result.msg);
			            },'json');
			    	}
			});
		});
		
	});

</script>
<div class="am-form">
  <fieldset>
    <input type="hidden" id="actId" value="${actId}"/>
    <div class="am-form-group">
      <label for="doc-vld-name-2">活动名称：</label>
      <input name="name" type="text" id="doc-vld-name-2" value="${name}" readonly>
    </div>

    <div class="am-form-group">
      <label for="doc-vld-email-2">开始时间：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${startTime}" readonly/>
    </div>
    
     <div class="am-form-group">
      <label for="doc-vld-email-2">结束时间：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${endTime}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">所在社区：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${areaName}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">报名上限：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${persionNum}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">剩余名额：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${syNum}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">每人积分：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${perIntegral}" readonly/>
    </div>
    <div class="am-form-group">
      <label for="doc-vld-email-2">活动状态：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${statusDesc}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">活动内容：</label>
      <textarea id="doc-vld-ta-2" name="address" maxlength="100" rows="4" readonly>${content}</textarea>
    </div>
    
    <button id="hdbm" class="am-btn am-btn-danger am-btn-block am-round" type="button">活动报名</button>
    <button id="hdqd" class="am-btn am-btn-danger am-btn-block am-round" type="button">活动签到</button>
    <button id="hdqt" class="am-btn am-btn-danger am-btn-block am-round" type="button">活动签退</button>
    <c:if test="${isAdmin==true}">
    	 <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
    	<button id="hddb" class="am-btn am-btn-danger am-btn-block am-round" type="button">代为报名</button>
     	<button id="hddq" class="am-btn am-btn-danger am-btn-block am-round" type="button">代为签到</button>
     	<button id="hddt" class="am-btn am-btn-danger am-btn-block am-round" type="button">代为签退</button>
<!--      	<hr data-am-widget="divider" style="" class="am-divider am-divider-default" /> -->
<!--       	<button id="hdstart" class="am-btn am-btn-danger am-btn-block am-round" type="button">活动开始</button> -->
<!--       	<button id="hdend" class="am-btn am-btn-danger am-btn-block am-round" type="button">活动结束</button> -->
    </c:if>
  </fieldset>
</div>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>