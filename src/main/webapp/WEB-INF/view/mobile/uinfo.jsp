<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>
<form class="am-form">
  <fieldset>
    <div class="am-form-group">
      <label for="doc-vld-name-2">姓名：</label>
      <input name="name" type="text" id="doc-vld-name-2" value="${name}" readonly/>
    </div>

    <div class="am-form-group">
      <label for="doc-vld-email-2">手机号：</label>
      <input name="mobile" type="tel" id="doc-vld-email-2" value="${mobile}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">所在社区：</label>
      <input name="mobile" type="tel" id="doc-vld-email-2" value="${areaName}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">可用积分：</label>
      <input name="mobile" type="tel" id="doc-vld-email-2" value="${integral}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">贡献度：</label>
      <input name="mobile" type="tel" id="doc-vld-email-2" value="${degree}" readonly/>
    </div>
    
    <div class="am-form-group">
      <label for="doc-vld-email-2">义工状态：</label>
      <input name="mobile" type="tel" id="doc-vld-email-2" value="${statusDesc}" readonly/>
    </div>
  </fieldset>
</form>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>