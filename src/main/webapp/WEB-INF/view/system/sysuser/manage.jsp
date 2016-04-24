<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/raty/jquery.raty.min.js"></script>
<script type="text/javascript">
	var url;
	
	//打开新增表单
	function add() {
	    $('#dlg').dialog('open').dialog('setTitle','<spring:message code="system.adduser"/>');
	    $('#form').form('clear');
	    $('#providerTd').raty({ scoreName: 'providerTd',score: 0 });
		$('#providerXl').raty({ scoreName: 'providerXl',score: 0 });
	    url = '${ctx}/system/sysuser/add';
	}
	
	//打开编辑表单
	function edit() {
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','<spring:message code="system.edituser"/>');
            $('#form').form('load',row);
            $('#providerTd').raty({ scoreName: 'providerTd',score: row.providerTd });
    		$('#providerXl').raty({ scoreName: 'providerXl',score: row.providerXl });
            url = '${ctx}/system/sysuser/edit';
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
	
	//打开管理角色菜单表单
	function editUserRole() {
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	if(row.id == 1) {
        		$.messager.show({
                    title:'<spring:message code="msg.warn"/>',
                    msg:'超级管理员不可设置角色'
            	});
        		return;
        	}
        	$('#eurForm').form('load',row);
        	$('#tree').tree('reload');
        	$('#editUserRole').dialog('open').dialog('setTitle','管理用户角色');
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
	
	//重置密码
	function resetPwd() {
		var rows = $('#dg').datagrid('getSelections');
        if (rows.length==1){
        	$.messager.confirm('<spring:message code="msg.confirm"/>','<spring:message code="system.sureResetPwd"/>',function(r){
                if (r){
            		$.post('${ctx}/system/sysuser/resetPwd',{id:rows[0].id},function(result){
            			 $.messager.show({
            	                title:'<spring:message code="msg.info"/>',
            	                msg:'<spring:message code="system.resetPwdSuccess"/>'
            	             });
                     },'json');
                }
            });
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
</script>
<!-- 列表 -->
<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false,striped:true"
    toolbar="#searchBar" pagination="true" idField="id" rownumbers="true"
    url="${ctx}/system/sysuser/list">
	<thead>
		<tr>
			<th field='id' checkbox='true' width="50"></th>
			<th field=username width="100px" sortable="true"><spring:message code="system.username"/></th>
			<th field="realname" width="70px" sortable="true"><spring:message code="system.realname"/></th>
			<th field="email" width="150px" sortable="true"><spring:message code="system.email"/></th>
			<th field="mobile" width="80px" sortable="true"><spring:message code="system.mobile"/></th>
			<th field="roles" width="150px">权限</th>
			<th field="createBy" width="80px" sortable="true"><spring:message code="entity.createBy"/></th>
			<th field="createTime" width="123px" sortable="true"><spring:message code="entity.createTime"/></th>
			<th field="updateBy" width="80px" sortable="true"><spring:message code="entity.updateBy"/></th>
			<th field="updateTime" width="123px" sortable="true"><spring:message code="entity.updateTime"/></th>
			<th field="sysInitDesc" width="60px" sortable="false"><spring:message code="entity.sysInit"/></th>
			<th field="remark" width="200px" sortable="false"><spring:message code="entity.remark"/></th>
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
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDgRow('dg','${ctx}/system/sysuser/del')">
		<spring:message code="op.delBtn"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-resetpwd" plain="true" onclick="resetPwd()">
		<spring:message code="system.resetPwd"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUserRole()">
		管理用户角色
	</a>
	<div>
	<form id="searchForm">
		<spring:message code="system.username"/> <input name="username" class="easyui-textbox">
		<spring:message code="system.email"/> <input name="email" class="easyui-textbox">
		<spring:message code="system.mobile"/> <input name="mobile" class="easyui-textbox">
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
			<label><spring:message code="system.username"/></label>
			<input name=username class="easyui-textbox easyui-validatebox" required="true">
		</div>
		<div class="fitem">
			<label><spring:message code="system.realname"/></label>
			<input name=realname class="easyui-textbox easyui-validatebox" required="true">
		</div>
		<div class="fitem">
			<label><spring:message code="system.email"/></label>
			<input name="email" class="easyui-textbox easyui-validatebox" validType="email">
		</div>
		<div class="fitem">
			<label><spring:message code="system.mobile"/></label>
			<input name="mobile" class="easyui-textbox">
		</div>
		<div class="fitem" style="display: inline-block;">
			<label>供应商态度</label>
			<span id="providerTd"></span>
		</div>
		<div class="fitem">
			<label>供应商效率</label>
			<span id="providerXl"></span>
		</div>
		<div class="fitem">
			<label><spring:message code="entity.remark"/></label>
			<input name="remark"  class="easyui-textbox" data-options="multiline:true" style="height:100px;"></input>
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
<script type="text/javascript">
	$(function() {
		$.fn.raty.defaults.path = '/plugins/raty/img';
	});
</script>
<jsp:include page="editUserRole.jsp"></jsp:include>