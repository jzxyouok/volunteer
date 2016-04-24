<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>
	<c:if test="${nofamily == false }">
		<div class="am-alert" data-am-alert style="margin-top: 1px;margin-bottom: -1px">
		  <ul>
		    <li>编号:${family.code }</li>
		    <li>名称:${family.name }</li>
		    <li>地址:${family.address }</li>
		  </ul>
		</div>
	</c:if>
	
	<div data-am-widget="titlebar" class="am-titlebar am-titlebar-multi" style="margin-top: 0px">
	    <h2 class="am-titlebar-title ">
	       	<i class="am-icon-users"></i> 成员列表
	    </h2>
	
	    <nav class="am-titlebar-nav">
	    	<c:if test="${nofamily == true }">
		        <a href="${ctx }/mobile/family/tocreate" class="">创建</a>
		        <a href="javascript:void(0)" onclick="join()" class="">加入</a>
			</c:if>
			<c:if test="${nofamily == false }">
				<a href="javascript:void(0)" onclick="jfzh()" class="">积分转换</a>
		        <a href="javascript:void(0)" onclick="quit()" class="">退出</a>
			</c:if>
	    </nav>
	</div>
	<ul class="am-list am-list-static" style="margin-top: -1px;margin-left: 10px">
		<c:forEach var="s" items="${members }" varStatus="status">
			<li>
				<c:if test="${s.familyOwner!='' }">
					<span class="am-badge am-badge-danger">
						${s.familyOwner}
					</span>
				</c:if>
				<c:if test="${s.familyMe!='' }">
					<span class="am-badge am-badge-success">
						${s.familyMe}
					</span>
				</c:if>
				<span>
			    	<i class="am-icon-user" style="color:#0E90D4"></i>
			    </span>
    			&nbsp;${s.name }(积分:${s.integral })
    		</li>
		</c:forEach>
	</ul>
<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd">
      您确定退出当前家庭？
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="join-prompt">
  <div class="am-modal-dialog">
    <div id="ex-name" class="am-modal-hd">加入家庭</div>
    <div class="am-modal-bd">
      	<input type="tel" placeholder="请输入家庭编号" class="am-modal-prompt-input">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="jfzh-prompt">
  <div class="am-modal-dialog">
    <div id="ex-name" class="am-modal-hd">积分转换</div>
    <div class="am-modal-bd">
    	<select id="targetId"  name="targetId" class="am-modal-prompt-input" >
       	  <c:forEach var="s" items="${members }" varStatus="status">
       	  	<c:if test="${me.id != s.id }">
       	  		<option value="${s.id }">${s.name }</option>
       	  	</c:if>
       	  </c:forEach>
       </select>
      	<input type="tel" placeholder="请输入转换积分" class="am-modal-prompt-input">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
    </div>
  </div>
</div>

<script type="text/javascript">
	function quit() {
		$('#my-confirm').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	  $.post('${ctx}/mobile/family/quit',{},function(result){
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
	
	function join() {
		$('#join-prompt').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	var codeV = e.data;
		    	$.post('${ctx}/mobile/family/join',{code:codeV},function(result){
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
	
	function jfzh() {
		$('#jfzh-prompt').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	var arrs = e.data;
		    	$.post('${ctx}/mobile/family/jfzh',{targetId:arrs[0],jifen:arrs[1]},function(result){
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