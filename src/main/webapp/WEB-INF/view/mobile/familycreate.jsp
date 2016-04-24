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
<form action="${ctx}/mobile/family/create" method="post" class="am-form" data-am-validator>
  <fieldset>
    <div class="am-form-group">
      <label for="doc-vld-name-2">家庭名称：</label>
      <input name="name" type="text" id="doc-vld-name-2" minlength="2" placeholder="输入家庭名称" required/>
    </div>

     <div class="am-form-group">
       <label for="doc-vld-ta-2">详细地址：</label>
       <textarea id="doc-vld-ta-2" name="address" placeholder="输入详细地址" maxlength="100" rows="4"></textarea>
     </div>

    <button class="am-btn am-btn-danger am-btn-block am-round" type="submit">提交</button>
  </fieldset>
</form>
</div>
</div>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>