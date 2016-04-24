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
		//扫描代报
		$('#dwdh').click(function () {
			wx.scanQRCode({
			        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			        success: function (res) {
			        	var $btn = $('#dwdh');
						$btn.button('loading');
			        	var url = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			        	$.post('${ctx}/mobile/ex/dwdh',{exId:$('#exId').val(),qrCodeUrl:url},function(result){
			  			$btn.button('reset');
			  			  if(result.success) {
			  				 $('#ex-name').html('${ex.name}');
			  				 $('#ex-tip').html('代兑者:' + result.data.persionName + '<br/>剩余可兑换数:${ex.remainNum }');
			  				 $('#ex-id').val('${ex.id}');
			  				 $('#ex-prompt').modal({
			  				      relatedTarget: this,
			  				      onConfirm: function(e) {
			  				    	
			  				    	var num = e.data;
			  				    	$.post('${ctx}/mobile/ex/dwex',{exId:$('#exId').val(),exNum:num,persionId:result.data.persionId},function(result){
			  				    		alert(result.msg);
			  				    		if(result.success) {
			  				    			location.reload(true);
			  				    		} 
			  				          },'json');
			  				      },
			  				      onCancel: function(e) {
//			  		 		        ignore
			  				      }
			  				    });
			  				  
			  				//dexchange('${ex.id}','${ex.name}','${ex.remainNum }',msg.data.persionId,msg.data.persionName);
			  			  } else {
			  				 alert(result.msg);
			  			  }
			             
			            },'json');
			    	}
			});
		});
		
		
	});

</script>
<div class="am-form">
  <fieldset>
    <input type="hidden" id="exId" value="${ex.id}"/>
    <div class="am-form-group">
      <label for="doc-vld-name-2">兑换品名称：</label>
      <input name="name" type="text" id="doc-vld-name-2" value="${ex.name}" readonly/>
    </div>

    <div class="am-form-group">
      <label for="doc-vld-email-2">服务商：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${ex.providerName}" readonly/>
    </div>
    
     <div class="am-form-group">
      <label for="doc-vld-email-2">总数量：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${ex.totalNum}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">剩余数量：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${ex.remainNum}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">所需积分：</label>
      <input name="mobile" type="text" id="doc-vld-email-2" value="${ex.needIntegral}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">活动内容：</label>
      <textarea id="doc-vld-ta-2" name="address" maxlength="100" rows="4" readonly>${ex.content}</textarea>
    </div>
    
    <button id="hdbm" class="am-btn am-btn-danger am-btn-block am-round" type="button" onclick="exchange('${ex.id}','${ex.name}','${ex.remainNum }')">兑换</button>
    <c:if test="${isAdmin!=true}">
    	<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
    	<button id="dwdh" class="am-btn am-btn-danger am-btn-block am-round" type="button">代为兑换</button>
    </c:if>
  </fieldset>
</div>

<!-- 兑换Prompt -->
<div class="am-modal am-modal-prompt" tabindex="-1" id="ex-prompt">
  <div class="am-modal-dialog">
    <input type="hidden" id="ex-id">
    <div id="ex-name" class="am-modal-hd">兑换品</div>
    <div class="am-modal-bd">
    	<span id="ex-tip">剩余可兑换数:0</span>
      	<input type="tel" placeholder="请输入兑换数量" class="am-modal-prompt-input">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
    </div>
  </div>
</div>

<script type="text/javascript">
    /**兑换物品*/
	function exchange(exIdV,exName,remainNum) {
		$('#ex-name').html(exName);
		$('#ex-tip').html('剩余可兑换数：' + remainNum);
		$('#ex-id').val(exIdV);
		$('#ex-prompt').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	var num = e.data;
		    	$.post('${ctx}/mobile/ex/exchange',{exId:$('#ex-id').val(),exNum:num},function(result){
		    		alert(result.msg);
		    		if(result.success) {
		    			location.reload(true);
		    		} 
		          },'json');
		      },
		      onCancel: function(e) {
// 		        ignore
		      }
		    });
	}
    
	/**兑换物品*/
	function dexchange(exIdV,exName,remainNum,persionId,persionName) {
		$('#ex-name').html(exName);
		$('#ex-tip').html('代兑者:' + persionName + '<br/>剩余可兑换数:' + remainNum);
		$('#ex-id').val(exIdV);
		$('#ex-prompt').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	var num = e.data;
		    	$.post('${ctx}/mobile/ex/dwex',{exId:$('#ex-id').val(),exNum:num,persionId},function(result){
		    		alert(result.msg);
		    		if(result.success) {
		    			location.reload(true);
		    		} 
		          },'json');
		      },
		      onCancel: function(e) {
// 		        ignore
		      }
		    });
	}
</script>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>