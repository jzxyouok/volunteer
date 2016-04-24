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
			<th>兑换品</th>
			<th>积分消耗</th>
			<th>兑换码</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="s" items="${exlist }" varStatus="status">
			<tr>
				<td>${s.exName } </td>
				<td>${s.perIntegral } x ${s.num }</td>
				<td>${s.exCode } </td>
				<td>${s.statusDesc } </td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>