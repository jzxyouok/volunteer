<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	
</script>
<!-- 列表 -->
<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false,striped:true"
    toolbar="#searchBar" pagination="true" idField="id" rownumbers="true" pageSize=20
    url="${ctx}/volun/family/list">
	<thead>
		<tr>
			<th field='id' checkbox='true' width="50"></th>
			<th field=code width="100px" sortable="true">家庭名字</th>
			<th field=name width="150px" sortable="true">家庭名字</th>
			<th data-options="field:'ownerName'" width="150px" sortable="true">拥有者</th>
			<th data-options="field:'address'" width="300px" sortable="true">详细地址</th>
		</tr>
	</thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()"> -->
<!-- 		<spring:message code="op.addBtn"/> -->
<!-- 	</a> -->
<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()"> -->
<!-- 		<spring:message code="op.editBtn"/> -->
<!-- 	</a> -->
<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDgRow('dg','${ctx}/volun/area/del')"> -->
<!-- 		<spring:message code="op.delBtn"/> -->
<!-- 	</a> -->
	<div>
	<form id="searchForm">
		家庭名字<input name="name" class="easyui-textbox">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchForm('dg','searchForm')"><spring:message code="op.queryBtn"/></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="reSearchForm('dg','searchForm')"><spring:message code="op.resetBtn"/></a>
	</form>
    </div>
</div>

<!-- 表单 -->
<div id="dlg" class="easyui-dialog" modal='true' style="padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
	<form id="form" method="post" novalidate>
		<input type="hidden" name="id"/>
		<div class="fitem">
			<label>社区名字</label>
			<input name=name class="easyui-textbox easyui-validatebox" required="true">
		</div>
		<div class="fitem">
			<label>排序号</label>
			<input name="sn" class="easyui-numberspinner" data-options="min:1,max:99999"></input>
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">
		<spring:message code="op.saveBtn"/></a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">
		<spring:message code="op.cancelBtn"/>
	</a>
</div>
