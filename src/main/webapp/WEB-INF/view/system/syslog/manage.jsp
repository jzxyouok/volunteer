<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<!-- 列表 -->
<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false"
    toolbar="#searchBar" pagination="true" idField="id" rownumbers="true"
    url="${ctx }/system/syslog/list">
	<thead>
		<tr>
			<th field="ownmodule" width="130px"><spring:message code="system.ownmodule"/></th>
			<th field="opType" width="130px"><spring:message code="system.opType"/></th>
			<th field="opContent" width="300px"><spring:message code="system.opContent"/></th>
			<th field="opUser" width="130px"><spring:message code="system.opUser"/></th>
			<th field="opDate" width="130px"><spring:message code="system.opDate"/></th>
			<th field="opIp" width="130px"><spring:message code="system.opIp"/></th>
		</tr>
	</thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<form id="searchForm">
		<spring:message code="system.opUser"/> <input name="opUser" class="easyui-textbox">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchFun()"><spring:message code="op.queryBtn"/></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetFun()"><spring:message code="op.resetBtn"/></a>
	</form>
</div>

<script type="text/javascript">
<!--
//查询函数
function searchFun() {
	$('#dg').datagrid('load', $.serializeObject($('#searchForm')));
	$('#dg').datagrid('clearSelections');
}

//重置函数
function resetFun() {
	$('#searchForm').form('clear');
	$('#dg').datagrid('load', $.serializeObject($('#searchForm')));
	$('#dg').datagrid('clearSelections');
}
//-->
</script>