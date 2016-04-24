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
<form action="${ctx}/mobile/persion/doJoinvolun" method="post" class="am-form" data-am-validator>
  <input type="hidden" name="wxOpenId" value="<c:out value="${openId}"></c:out>">
  <fieldset>
    <div class="am-form-group">
      <label for="doc-vld-name-2">姓名：</label>
      <input name="name" type="text" id="doc-vld-name-2" minlength="2" placeholder="输入用户名" required/>
    </div>

    <div class="am-form-group">
      <label for="doc-vld-email-2">手机号：</label>
      <input name="mobile" type="tel" id="doc-vld-email-2" placeholder="输入手机号" required/>
    </div>

	<div class="am-form-group">
      <label>性别： </label>
      <label class="am-radio-inline">
        <input type="radio" value="man" name="sex" checked="checked" required> 男
      </label>
      <label class="am-radio-inline">
        <input type="radio" value="woman" name="sex"> 女
      </label>
      <label class="am-radio-inline">
        <input type="radio" value="unkown" name="sex"> 未知
      </label>
    </div>
    
    <div class="am-form-group">
      <label for="doc-select-1">所在社区</label>
      <select id="doc-select-1"  name="areaId" required>
       	  <option value="">-=请选择=-</option>
	      <c:forEach var="area" items="${areas}">
	      	<option value="${area.id}">${area.name}</option>
	      </c:forEach>
      </select>
      <span class="am-form-caret"></span>
    </div>

    <div class="am-form-group">
      <label for="doc-vld-url-2">出生日期：</label>
      <input name="birthday" type="text" class="am-form-field" placeholder="设置出生日期" data-am-datepicker readonly required/>
    </div>

    <div class="am-form-group">
      <label for="doc-vld-ta-2">详细地址：</label>
      <textarea id="doc-vld-ta-2" name="address" maxlength="100" rows="4"></textarea>
    </div>

    <button class="am-btn am-btn-danger am-btn-block am-round" type="submit">提交</button>
  </fieldset>
</form>
</div>
</div>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>