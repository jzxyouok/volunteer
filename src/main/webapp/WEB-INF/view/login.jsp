<%@page import="com.comiyun.core.util.AppUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="os.softName"/></title>
<meta charset="UTF-8" />
<!-- logo -->
<link rel="icon" href="<c:url value="/styles/favicon.ico" />" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="styles/login/reset.css">
<link rel="stylesheet" type="text/css" href="styles/login/structure.css">
<script type="text/javascript">
	if (window != top) {
		top.location.href = location.href; 
	}
</script>
</head>
<body>

<div class="login_logo">
	<img style="width: 230px;height: 120px;" src="styles/images/logo.jpg" />
</div>
<c:url value='/login' var="loginFormAction" />
<form class="box login" method="post" action="${loginFormAction}">
	<fieldset class="boxBody">
	  <input name="username" type="text" tabindex="1" placeholder="<spring:message code="login.name"/>" required>
	  <input name="password" type="password" tabindex="2" placeholder="<spring:message code="login.password"/>" required>
	  <%
	  	if(AppUtil.getConfigBoolean("login.captchaEnable")) {
	  %>
	  <table>
	  	<tr>
	  		<td><input name="captchaCode" type="text" style="width: 140px;" tabindex="3" placeholder="<spring:message code="login.validateCode"/>" required></td>
	  		<td><img id="captchaImg" src="captcha.jpg"/></td>
	  	</tr>
	  </table>
	  <%} %>
	  
	</fieldset>
	<footer>
	  <input type="reset" class="btnLogin" value="<spring:message code="login.resetBtn"/>" tabindex="4">
	  <input type="submit" class="btnLogin" value="<spring:message code="login.loginBtn"/>" tabindex="4">
	</footer>
</form>
</body>
</html>