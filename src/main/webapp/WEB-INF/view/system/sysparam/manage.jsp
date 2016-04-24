<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	
	//打开新增表单
	function add() {
	    $('#dlg').dialog('open').dialog('setTitle','<spring:message code="system.adduser"/>');
	    $('#form').form('clear');
	    url = '${ctx}/system/sysparam/add';
	}
	
	//打开编辑表单
	function edit() {
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','<spring:message code="system.edituser"/>');
            $('#form').form('load',row);
            url = '${ctx}/system/sysparam/edit';
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
            	if(true) {
            		$('#dlg').dialog('close');
                    $('#dg').datagrid('reload');
                    $('#dg').datagrid('clearSelections');
                    $.messager.show({
			                title:'<spring:message code="msg.info"/>',
			                msg:'<spring:message code="msg.savesuccess"/>'
			        	});
            	}
            }
        });
    }
	
	//删除行
	function del(){
         var rows = $('#dg').datagrid('getSelections');
         if (rows.length>=1){
             $.messager.confirm('<spring:message code="msg.confirm"/>','<spring:message code="msg.sureDelRows"/>',function(r){
                 if (r){
                     $.post('${ctx}/system/sysparam/del',{ids:resolveIds(rows)},function(result){
                         if (true){
                        	 $('#dg').datagrid('reload');
                        	 $('#dg').datagrid('clearSelections');
                        	 $.messager.show({
     			                title:'<spring:message code="msg.info"/>',
     			                msg:'<spring:message code="msg.delsuccess"/>'
     			             });
                         } else {
                             $.messager.show({
                                 title: 'Error',
                                 msg: result.errorMsg
                             });
                         }
                     },'json');
                 }
             });
		} else {
        	 $.messager.show({
                 title:'<spring:message code="msg.warn"/>',
                 msg:'<spring:message code="msg.noSelectRow"/>'
         	});
		}
	}
	
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
</script>
<!-- 列表 -->
<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false,striped:true"
    toolbar="#searchBar" pagination="true" idField="id" rownumbers="true"
    url="${ctx}/system/sysparam/list">
	<thead>
		<tr>
			<th field='id' checkbox='true' width="50"></th>
			<th field=paramName width="150px" sortable="true"><spring:message code="system.paramName"/></th>
			<th field="dataType" width="100px" sortable="true"><spring:message code="system.dataType"/></th>
			<th field="paramValue" width="150px" sortable="true"><spring:message code="system.paramValue"/></th>
			<th field="sysInitDesc" width="60px" sortable="false"><spring:message code="entity.sysInit"/></th>
			<th field="remark" width="450px"><spring:message code="entity.remark"/></th>
		</tr>
	</thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">
		<spring:message code="op.addBtn"/>
	</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">
		<spring:message code="op.editBtn"/>
	</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">
		<spring:message code="op.delBtn"/>
	</a>
	<div>
	<form id="searchForm">
		<spring:message code="system.paramName"/> <input name="paramName" class="easyui-textbox">
		<spring:message code="system.dataType"/>
		<input name="dataType"
            class="easyui-combobox" 
            data-options="
                    url:'${ctx}/system/sysparam/datatypelist',
                    editable:false,
                    method:'get',
                    valueField:'type',
                    textField:'type',
                    panelHeight:'auto'
            ">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchFun()"><spring:message code="op.queryBtn"/></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetFun()"><spring:message code="op.resetBtn"/></a>
	</form>
    </div>
</div>

<!-- 表单 -->
<div id="dlg" class="easyui-dialog" modal='true' style="padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
	<form id="form" method="post" novalidate>
		<input type="hidden" name="id"/>
		<div class="fitem">
			<label><spring:message code="system.paramName"/></label>
			<input name=paramName class="easyui-textbox easyui-validatebox" required="true">
		</div>
		<div class="fitem">
			<label><spring:message code="system.dataType"/></label>
			<input name="dataType"
	            class="easyui-combobox" 
	            data-options="
	                    url:'${ctx}/system/sysparam/datatypelist',
	                    editable:false,
	                    method:'get',
	                    valueField:'type',
	                    textField:'name',
	                    panelHeight:'auto'
	            ">
		</div>
		<div class="fitem">
			<label><spring:message code="system.paramValue"/></label>
			<input name="paramValue" class="easyui-textbox">
		</div>
		<div class="fitem">
			<label><spring:message code="entity.remark"/></label>
			<input name="remark"  class="easyui-textbox" data-options="multiline:true" style="height:100px;"></input>
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
