<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>

<c:forEach var="s" items="${exlist }" varStatus="status">
	<div class="am-panel am-panel-secondary">
	  <div class="am-panel-hd">
	  	<span class="am-icon-bookmark">
	  	<a href="${ctx }/mobile/ex/exinfo?exId=${s.id}" class="am-list-item-hd ">${s.name }</a>
	  	</span>
	  	<span class="am-icon-user" style="float:right"> ${s.providerName }</span>
	  </div>
	  <div class="am-panel-bd">
	  	<figure data-am-widget="figure" class="am am-figure am-figure-default ">
      	  <img src="${ctx }/weixin/qrcode/get?id=${s.qrCodeId}" style="width: 150px;height: 150px"/>
          <figcaption class="am-figure-capition-btm">
            ${s.content }
          </figcaption>
  		</figure>
	  </div>
	  <footer class="am-panel-footer">
	  	<div class="am-g am-g-collapse">
		  <div class="am-u-sm-7">
        	<span class="am-badge am-badge-warning am-round">积分:${s.needIntegral }</span>
		  	<span class="am-badge am-badge-primary am-round">总数:${s.totalNum }</span>
        	<span class="am-badge am-badge-success am-round">剩余:${s.remainNum } </span>
		  </div>
		  <span class="am-u-sm-3">
<!-- 		  	 <a id="ex-btn" class="am-btn am-btn-danger am-btn-xs" style="float:right" href="javascript:void(0)" onclick="exchange('${s.id}','${s.name}','${s.remainNum }')"> -->
<!-- 				  <i class="am-icon-shopping-cart">兑换</i> -->
<!-- 			 </a> -->
			 <a id="ex-btn" class="am-btn am-btn-danger am-btn-xs" style="float:right" href="${ctx }/mobile/ex/exinfo?exId=${s.id}">
				  <i class="am-icon-shopping-cart">详情</i>
			 </a>
		  </span>
		</div>
	  </footer>
	</div>
</c:forEach>

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
	
</script>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>