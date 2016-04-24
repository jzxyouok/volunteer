<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<!-- 列表 -->
<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false,nowrap:false"
    toolbar="#searchBar" pagination="true" idField="id" rownumbers="true"
    url="${ctx }/system/feedback/list">
	<thead>
		<tr>
			<th field="mobile" width="130px">手机号</th>
			<th field="qq" width="130px">QQ</th>
			<th field="email" width="130px">邮箱</th>
			<th field="content" width="300px">反馈内容</th>
			<th field="createTime" width="130px">反馈时间</th>
		</tr>
	</thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<form id="searchForm">
		联系方式 <input name="keyword" class="easyui-textbox">
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