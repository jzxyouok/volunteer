<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/view/includes/mMeta.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/view/includes/mHeader.jsp"%>

<table class="am-table am-table-striped am-table-hover ">
	<thead>
		<tr>
			<th>内容</th>
			<th>积分</th>
			<th>时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="s" items="${jfList }" varStatus="status">
			<tr>
				<td>${s.bizContent } </td>
				<td>${s.digit } </td>
				<td>${s.createTimeStr } </td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>