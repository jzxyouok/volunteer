<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	
	var actid;
	
	//打开新增表单
	function add() {
		$('#pidCombo').combobox('reload');
	    $('#dlg').dialog('open').dialog('setTitle','新增活动');
	    $('#form').form('clear');
	    url = '${ctx}/volun/activity/add';
	}
	
	//打开编辑表单
	function edit() {
		$('#pidCombo').combobox('reload');
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','编辑活动');
            $('#form').form('load',row);
            url = '${ctx}/volun/activity/edit';
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
	
	function changestatus(status) {
		var rows = $("#dg").datagrid('getSelections');
        if (rows.length==1){
        	$.post('${ctx}/volun/activity/changestatus',{id:rows[0].id,status:status},function(result){
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
		} else if(rows.length>=1) {
			$.messager.show({
                title:'<spring:message code="msg.warn"/>',
                msg:'只能选择一条数据'
        	});
		} else {
	       	 $.messager.show({
	                title:'<spring:message code="msg.warn"/>',
	                msg:'<spring:message code="msg.noSelectRow"/>'
	        	});
		}
	}
	
	//打开新增表单
	function bmdlg() {
		var rows = $("#dg").datagrid('getSelections');
        if (rows.length==1){
        	var row = rows[0];
        	actid = row.id;
        	if(row.status != 'publish') {
        		$.messager.show({
                    title:'<spring:message code="msg.warn"/>',
                    msg:'当前活动不可报名'
            	});
        		return;
        	}
        	
        	$('#bmdlg').dialog('open').dialog('setTitle','活动报名');
        	
		} else if(rows.length>=1) {
			$.messager.show({
                title:'<spring:message code="msg.warn"/>',
                msg:'只能选择一条数据'
        	});
		} else {
	       	 $.messager.show({
	                title:'<spring:message code="msg.warn"/>',
	                msg:'<spring:message code="msg.noSelectRow"/>'
	        	});
		}
	}
	
	function bmsave() {
		var rows = $("#persiondg").datagrid('getSelections');
        if (rows.length!=0){
        	$.post('${ctx}/volun/activitypersion/adminbm',{actId:actid,ids:resolveIds(rows)},function(result){
                if (result.success){
               	 $.messager.show({
		                title:'<spring:message code="msg.info"/>',
		                msg:result.msg
		             });
               	$('#bmdlg').dialog('close');
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
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="width:750px;margin:20px 0;" data-options="
                url: '${ctx}/volun/activity/list',
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
                <th data-options="field:'code'" width="70" sortable="true">活动代码</th>
                <th data-options="field:'name'" width="85" sortable="true">活动名称</th>
                <th data-options="field:'content'" width="300" sortable="true">活动内容</th>
                <th data-options="field:'persionNum'" width="60" sortable="true">预参人数</th>
                <th data-options="field:'perIntegral'" width="60" sortable="true">每人积分</th>
                <th data-options="field:'areaName'" width="120" sortable="true">所属社区</th>
				<th field="startTime" width="123px" sortable="true" sortable="true">开始时间</th>
				<th field="endTime" width="123px" sortable="true" sortable="true">结束时间</th>
				<th field="qrCodeId" width="75px" formatter="qrcodeUrl">二维码</th>
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
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDgRow('dg','${ctx}/volun/activity/del')">
		<spring:message code="op.delBtn"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-publish" plain="true" onclick="changestatus('publish')">
		发布活动
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unpublish" plain="true" onclick="changestatus('draft')">
		取消活动
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start" plain="true" onclick="changestatus('started')">
		开始活动
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop" plain="true" onclick="changestatus('stop')">
		结束活动
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop" plain="true" onclick="bmdlg()">
		活动报名
	</a>
	<div>
	<form id="searchForm">
		名字 <input name="name" class="easyui-textbox">
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
			<label style="float:left;">活动代码</label>
			<input name="code" class="easyui-textbox easyui-validatebox" required="true" style="width: 150px">
			<label>活动名称</label>
			<input name="name" class="easyui-textbox" required="true" style="width: 150px">
		</div>
		<div class="fitem">
			<label style="float:left;">开始时间</label>
			<input name="startTime" class="easyui-datetimebox" required="true" data-options="editable:false,showSeconds:false" style="width: 150px">
			<label>结束时间</label>
			<input name="endTime" class="easyui-datetimebox" required="true" data-options="editable:false,showSeconds:false" style="width: 150px">
		</div>
		<div class="fitem">
			<label style="float:left;">预计参加义工</label>
			<input name="persionNum" class="easyui-numberspinner" data-options="min:1,max:999999999" required="true" style="width: 150px"></input>
			<label style="width:120px;">本次活动每人次给予</label>
			<input name="perIntegral" class="easyui-numberspinner"  data-options="min:0,max:999999999"  required="true" style="width: 100px">分
		</div>
		<div class="fitem">
			<label style="float:left;">社区</label>
			<input name="areaId" class="easyui-combobox" required="true" 
				data-options="valueField:'id',textField:'name',editable:false,url:'/volun/area/selectList'" style="width: 150px">
		</div>
		<div class="fitem">
			<label style="float:left;">活动内容</label>
			<textarea name="content" class="easyui-textbox"  data-options="multiline:true" style="height:80px;width: 390px"></textarea>
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

<!-- 表单窗口 -->
<div id="bmdlg" class="easyui-dialog" modal='true' style=""
            closed="true" buttons="#bmdlg_buttons">
    <table id="persiondg" class="easyui-datagrid" style="width:550px;height:300px;" data-options="
                url: '${ctx}/volun/persion/birthlist',
                method: 'get',
                border:false,
                rownumbers: true,
                pagination: true,
                idField: 'id'
            ">
			<thead>
	            <tr>
	            	<th field='id' checkbox='true' width="50"></th>
	                <th data-options="field:'name'" width="70">姓名</th>
	                <th data-options="field:'mobile'" width="85">手机号</th>
	                <th data-options="field:'sexDesc'" width="40">性别</th>
	                <th data-options="field:'birthday'" width="73px">出生日期</th>
	                <th data-options="field:'areaName'" width="120">所属社区</th>
	                <th data-options="field:'statusDesc'" width="80">状态</th>
	            </tr>
	        </thead>
	</table>
	<div id="bmdlg_buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="bmsave()">
			<spring:message code="op.saveBtn"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bmdlg').dialog('close')">
			<spring:message code="op.cancelBtn"/>
		</a>
	</div>
</div>
