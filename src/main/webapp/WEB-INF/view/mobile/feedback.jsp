<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>
<div class="am-g">
  <div class="am-u-md-8 am-u-sm-centered">
    <form id="feedbackForm" class="am-form" method="post" action="${ctx}/mobile/feedback/tijiao" data-am-validator>
      <fieldset class="am-form-set">
        <div class="am-form-group">
        <label>手机：</label>
        <input id="mobileC" name="mobile" type="tel" placeholder="请输入手机号" required/>
        </div>
        <div class="am-form-group">
        <label >QQ：</label>
        <input id="qqC" name="qq" type="tel" placeholder="请输入QQ号" required/>
        </div>
        <div class="am-form-group">
        <label >邮箱：</label>
        <input id="emailC" name="email" type="email" placeholder="请输入邮箱" required/>
        </div>
        <div class="am-form-group">
        <label >内容：</label>
        <textarea id="contentC" name="content" minlength="10" maxlength="200" rows="3" placeholder="请输入反馈内容(10-200字)"></textarea>
        </div>
      </fieldset>
      <button type="submit" class="am-btn am-btn-danger am-btn-block am-round">提交反馈</button>
    </form>
  </div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$("#feedbackForm").ajaxForm(function(data) {
			 alert(data.msg);
			 location.reload(true);
		});
	});
</script>

<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>