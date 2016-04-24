<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	
	//打开新增表单
	function add() {
	    $('#dlg').dialog('open').dialog('setTitle','新增素材');
	    $('#form').form('clear');
	    url = '${ctx}/weixin/text/add';
	}
	
	//打开编辑表单
	function edit() {
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','编辑素材');
            $('#form').form('load',row);
            url = '${ctx}/weixin/text/update';
        } else if(rows.length>1) {
        	$.messager.show({
                title:'<spring:message code="msg.warn"/>',
                msg:'<spring:message code="msg.allowSelectOne"/>'
        	});
        } else {
        	$.messager.show({
                title:'<spring:message code="msg.warn"/>',
                msg:'<spring:message code="msg.noSelectRow"/>'
        	});
        }
    }
	
	//保存表单
	function save() {
        $('#form').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
            	var data = eval('(' + result + ')');
            	$.messager.show({
	                title:'<spring:message code="msg.info"/>',
	                msg:data.msg
	        	});
            	if(data.success) {
            		$('#dlg').dialog('close');
                    $('#dg').datagrid('reload');
                    $('#dg').datagrid('clearSelections');
            	}
            }
        });
    }
</script>
<!-- 列表 -->
<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false,striped:true"
    toolbar="#searchBar" pagination="true" idField="id" rownumbers="true"
    url="${ctx}/weixin/text/list">
	<thead>
		<tr>
			<th field='id' checkbox='true' width="50"></th>
			<th field=name width="100px" sortable="true">素材名字</th>
			<th field="content" width="450px" sortable="true">素材内容</th>
			<th field="createBy" width="100px" sortable="true"><spring:message code="entity.createBy"/></th>
			<th field="createTime" width="123px" sortable="true"><spring:message code="entity.createTime"/></th>
			<th field="updateBy" width="100px" sortable="true"><spring:message code="entity.updateBy"/></th>
			<th field="updateTime" width="123px" sortable="true"><spring:message code="entity.updateTime"/></th>
			<th field="sysInitDesc" width="60px" sortable="false"><spring:message code="entity.sysInit"/></th>
		</tr>
	</thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">
		<spring:message code="op.addBtn"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">
		<spring:message code="op.editBtn"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDgRow('dg','${ctx}/weixin/text/del')">
		<spring:message code="op.delBtn"/>
	</a>
<div>
	<form id="searchForm">
		素材名字 <input name="name" class="easyui-textbox">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchForm('dg','searchForm')"><spring:message code="op.queryBtn"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="reSearchForm('dg','searchForm')"><spring:message code="op.resetBtn"/></a>
	</form>
    </div>
</div>

<!-- 表单窗口 -->
<div id="dlg" class="easyui-dialog" modal='true' style="padding:10px 20px"
            closed="true" buttons="#dlg_buttons">
    <!-- 表单 -->
	<form id="form" method="post" novalidate>
		<input type="hidden" name="id"/>
		<div class="fitem">
			<label style="float:left;">素材名字 </label>
			<input name=name class="easyui-textbox easyui-validatebox" required="true" style="width: 360px">
		</div>
		<div class="fitem">
			<label style="float:left;">素材内容 </label>
			<input name="content"  class="easyui-textbox" data-options="multiline:true" style="height:250px;width: 360px"></input>
		</div>
	</form>
	<div id="dlg_buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">
			<spring:message code="op.saveBtn"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">
			<spring:message code="op.cancelBtn"/>
		</a>
	</div>
</div>
