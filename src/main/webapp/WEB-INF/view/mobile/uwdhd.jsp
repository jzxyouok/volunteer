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
			<th>活动名称</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="s" items="${actlist }" varStatus="status">
			<tr>
				<td>${s.actName } </td>
				<td>${s.actStartTimeStr} </td>
				<td>${s.actEndTimeStr } </td>
				<td>${s.statusDesc } </td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
<%@ include file="/WEB-INF/view/includes/mFooter.jsp"%>
</body>
</html>