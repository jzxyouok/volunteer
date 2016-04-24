<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	//打开新增表单
	function add() {
	    $('#dlg').dialog('open').dialog('setTitle','新增义工');
	    $('#form').form('clear');
	    url = '${ctx}/volun/persion/add';
	}
	
	//打开编辑表单
	function edit() {
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','编辑义工');
            $('#form').form('load',row);
            url = '${ctx}/volun/persion/edit';
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
	
	function audit() {
		var rows = $("#dg").datagrid('getSelections');
        if (rows.length>=1){
        	$.post('${ctx}/volun/persion/audit',{ids:resolveIds(rows)},function(result){
                if (result.success){
               	 $("#dg").datagrid('reload');
               	 $("#dg").datagrid('clearSelections');
               	 $.messager.show({
		                title:'<spring:message code="msg.info"/>',
		                msg:result.msg
		             });
                } else {
                    $.messager.show({
                        title: 'Error',
                        msg: result.msg
                    });
                }
            },'json');
		} else {
       	 $.messager.show({
                title:'<spring:message code="msg.warn"/>',
                msg:'<spring:message code="msg.noSelectRow"/>'
        	});
		}
	}
	
	function logoff() {
		var rows = $("#dg").datagrid('getSelections');
        if (rows.length>=1){
        	$.post('${ctx}/volun/persion/logoff',{ids:resolveIds(rows)},function(result){
                if (result.success){
               	 $("#dg").datagrid('reload');
               	 $("#dg").datagrid('clearSelections');
               	 $.messager.show({
		                title:'<spring:message code="msg.info"/>',
		                msg:result.msg
		             });
                } else {
                    $.messager.show({
                        title: 'Error',
                        msg: result.msg
                    });
                }
            },'json');
		} else {
       	 $.messager.show({
                title:'<spring:message code="msg.warn"/>',
                msg:'<spring:message code="msg.noSelectRow"/>'
        	});
		}
	}
	
	function setadmin() {
		var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	$.post('${ctx}/volun/persion/setadmin',{id:rows[0].id},function(result){
                if (result.success){
               	 $("#dg").datagrid('reload');
               	 $("#dg").datagrid('clearSelections');
               	 $.messager.show({
		                title:'<spring:message code="msg.info"/>',
		                msg:result.msg
		             });
                } else {
                    $.messager.show({
                        title: 'Error',
                        msg: result.msg
                    });
                }
            },'json');
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
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="width:750px;margin:20px 0;" data-options="
                url: '${ctx}/volun/persion/list',
                method: 'get',
                fit:true,
                border:false,
                rownumbers: true,
                pagination: true,
                idField: 'id'
            ">
		<thead>
            <tr>
            	<th field='id' checkbox='true' width="50"></th>
                <th data-options="field:'name'" width="70" sortable="true">姓名</th>
                <th data-options="field:'mobile'" width="85" sortable="true">手机号</th>
                <th data-options="field:'sexDesc'" width="40">性别</th>
                <th data-options="field:'birthday'" width="73px" sortable="true">出生日期</th>
                <th data-options="field:'channelDesc'" width="60">义工来源</th>
                <th data-options="field:'areaName'" width="120" sortable="true">所属社区</th>
                <th data-options="field:'familyName'" width="120" sortable="true">所属家庭</th>
				<th field="qrCodeId" width="75px" formatter="qrcodeUrl">二维码</th>
				<th data-options="field:'isAdminDesc'" width="80">是否工作人员</th>
                <th data-options="field:'statusDesc'" width="80">状态</th>
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
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDgRow('dg','${ctx}/volun/persion/del')">
		<spring:message code="op.delBtn"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-audit" plain="true" onclick="audit()">
		义工账户审核
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-logoff" plain="true" onclick="logoff()">
		义工账户注销
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-logoff" plain="true" onclick="setadmin()">
		设置是否工作人员
	</a>
	<div>
	<form id="searchForm">
		姓名 <input name="name" class="easyui-textbox" style="width: 70px">
		手机号 <input name="mobile" class="easyui-textbox" style="width: 100px">
		义工来源
		<select  name="channel" data-options="editable:false,panelHeight:'auto'" class="easyui-combobox" style="width:100px;">
		    <option value="">==请选择==</option>
		    <option value="wxreg">微信注册</option>
		    <option value="sysadd">系统添加</option>
		</select>
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
			<label style="float:left;">姓名</label>
			<input name="name" class="easyui-textbox easyui-validatebox" required="true" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">手机号</label>
			<input name="mobile" class="easyui-textbox" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">性别</label>
			<select name="sex" class="easyui-combobox" required="true" data-options="editable:false" style="width: 200px">
 			    <option value="man">男</option>
 			    <option value="woman">女</option>
 			    <option value="unkown">未知</option>
 			</select>
		</div>
		<div class="fitem">
			<label style="float:left;">出生日期</label>
			<input name="birthday" class="easyui-datebox" data-options="editable:false" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">所属社区</label>
			<input name="areaId" class="easyui-combobox" required="true" 
				data-options="valueField:'id',textField:'name',editable:false,url:'/volun/area/selectList'" style="width: 200px">
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
