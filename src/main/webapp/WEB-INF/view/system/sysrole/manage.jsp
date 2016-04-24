<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	
	$(function(){
		//加载角色树
		$('#tree').tree({
		    url:"${ctx}/system/sysmenu/allTree",
		    checkbox : true,
		    onLoadSuccess : function(node, data) {
			}
		    });
		});
	
	//打开新增表单
	function add() {
	    $('#dlg').dialog('open').dialog('setTitle','<spring:message code="system.addrole"/>');
	    $('#form').form('clear');
	    url = '${ctx}/system/sysrole/add';
	}
	
	//打开编辑表单
	function edit() {
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','<spring:message code="system.editrole"/>');
            $('#form').form('load',row);
            url = '${ctx}/system/sysrole/edit';
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
	
	function refreshRoleMenu(index,row) {
		$.post("${ctx}/system/sysmenurole/menuRoleByRoleId", {
			roleId : row.id
		}, function(result) {
			//清除原始节点
			var nodes = $('#tree').tree('getChecked');
			for(var j=0;j<nodes.length;j++) {
				$('#tree').tree('uncheck', nodes[j].target);
			}
			if (result) {
				for (var i = 0; i < result.length; i++) {
					var node = $('#tree').tree('find', result[i].menuId);
					if (node) {
						var isLeaf = $('#tree').tree('isLeaf', node.target);
						if (isLeaf) {
							$('#tree').tree('check', node.target);
						}
					}
				}
			}
		}, 'json');
	}
	
	//保存管理角色菜单
	function saveMenuRole() {
		var row = $('#dg').datagrid('getSelected');
		if(row == null) {
			 $.messager.show({
	                title:'<spring:message code="msg.info"/>',
	                msg:'请选择一条角色'
	        	});
		}
		var roleId =row.id;
		var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
		var ids = [];
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$.post('${ctx}/system/sysmenurole/editMenuRole', {
			roleId : roleId,
			ids : ids.join(',')
		}, function(result) {
			if (result.success){
			 $('#dg').datagrid('reload');
           	 $.messager.show({
	                title:'<spring:message code="msg.info"/>',
	                msg: result.msg
	             });
            } else {
                $.messager.show({
                    title: 'Error',
                    msg: result.msg
                });
            }
		}, 'json');
	}
</script>
<div class="easyui-layout" data-options="border:false" style="width:100%;height:100%;">
        <div data-options="region:'center',border:false" style="overflow: hidden;">
        	<!-- 列表 -->
			<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false,striped:true,singleSelect:true,onSelect:refreshRoleMenu"
			    toolbar="#searchBar" pagination="true" idField="id" rownumbers="true"
			    url="${ctx}/system/sysrole/list">
				<thead>
					<tr>
						<th field='id' checkbox='true' width="50"></th>
						<th field=roleName width="100px" sortable="true"><spring:message code="system.roleName"/></th>
						<th field="createBy" width="100px" sortable="true"><spring:message code="entity.createBy"/></th>
						<th field="createTime" width="123px" sortable="true"><spring:message code="entity.createTime"/></th>
						<th field="updateBy" width="100px" sortable="true"><spring:message code="entity.updateBy"/></th>
						<th field="updateTime" width="123px" sortable="true"><spring:message code="entity.updateTime"/></th>
						<th field="sysInitDesc" width="60px" sortable="false"><spring:message code="entity.sysInit"/></th>
						<th field="remark" width="200px" sortable="false"><spring:message code="entity.remark"/></th>
					</tr>
				</thead>
			</table>
			<!-- 工具条 -->
			<div id="searchBar" style="padding: 0px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">
					<spring:message code="op.addBtn"/>
				</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">
					<spring:message code="op.editBtn"/>
				</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDgRow('dg','${ctx}/system/sysrole/del')">
					<spring:message code="op.delBtn"/>
				</a>
				<div>
					<form id="searchForm">
						<spring:message code="system.roleName"/> <input name="roleName" class="easyui-textbox">
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
						<label><spring:message code="system.roleName"/></label>
						<input name=roleName class="easyui-textbox easyui-validatebox" required="true">
					</div>
					<div class="fitem">
						<label><spring:message code="entity.remark"/></label>
						<input name="remark" class="easyui-textbox" data-options="multiline:true" style="height:100px;"></input>
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
 		</div>
 		<!-- 权限菜单 -->
 		<div data-options="region:'east',split:true,border:false,tools:'#emr-buttons'" title="角色菜单" style="width:200px;">
	 		<!-- 表单 -->
			<form id="emrForm">
				<ul id="tree"></ul>
			</form>
			<div id="emr-buttons">
				<a href="javascript:void(0)" class="icon-save" onclick="saveMenuRole()"></a>
			</div>
        </div>
</div>

