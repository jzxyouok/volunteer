<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
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
        	$.post('${ctx}/volun/activitypersion/changestatus',{ids:resolveIds(rows),status:status},function(result){
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
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="width:750px;margin:20px 0;" data-options="
                url: '${ctx}/volun/activitypersion/list',
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
                <th data-options="field:'actCode'" width="100" sortable="true">活动代码</th>
                <th data-options="field:'actName'" width="300" sortable="true">活动名称</th>
                <th data-options="field:'persionName'" width="70" sortable="true">义工姓名</th>
                <th data-options="field:'persionMobile'" width="85" sortable="true">电话</th>
                <th data-options="field:'persionSexDesc'" width="40">性别</th>
                <th data-options="field:'resourceDesc'" width="60">来源</th>
				<th field="bmTime" width="123px" sortable="true" sortable="true">报名时间</th>
				<th field="qdTime" width="123px" sortable="true" sortable="true">签到时间</th>
				<th field="qtTime" width="123px" sortable="true" sortable="true">签退时间</th>
                <th data-options="field:'statusDesc'" width="80">状态</th>
            </tr>
        </thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="l-btn-icon icon-ok" plain="true" onclick="changestatus('cj')"> -->
<!-- 		参加确认 -->
<!-- 	</a> -->
	<div>
	<form id="searchForm">
		活动代码 <input name="actCode" class="easyui-textbox">
		活动名称<input name="actName" class="easyui-textbox">
		义工姓名<input name="persionName" class="easyui-textbox">
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
