<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	//打开新增表单
	function add() {
		$('#pidCombo').combobox('reload');
	    $('#dlg').dialog('open').dialog('setTitle','新增兑换品');
	    $('#form').form('clear');
	    url = '${ctx}/volun/exchange/add';
	}
	
	//打开编辑表单
	function edit() {
		$('#pidCombo').combobox('reload');
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','编辑兑换品');
            $('#form').form('load',row);
            url = '${ctx}/volun/exchange/edit';
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
	
	function processEx(s) {
		var rows = $("#dg").datagrid('getSelections');
        if (rows.length>=1){
        	$.post('${ctx}/volun/exchange/processEx',{ids:resolveIds(rows),status:s},function(result){
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
	
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="width:750px;margin:20px 0;" data-options="
                url: '${ctx}/volun/exchange/list',
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
                <th data-options="field:'name'" width="100" sortable="true">名字</th>
                <th data-options="field:'providerName'" width="100" sortable="true">特约服务商</th>
                <th data-options="field:'totalNum'" width="70" sortable="true">总数</th>
                <th data-options="field:'useNum'" width="70" sortable="true">已兑换</th>
                <th data-options="field:'remainNum'" width="70">剩余</th>
                <th data-options="field:'needIntegral'" width="70" sortable="true">所需积分</th>
                <th data-options="field:'content'" width="150" sortable="true">描述</th>
                <th field="createBy" width="100px" sortable="true"><spring:message code="entity.createBy"/></th>
				<th field="createTime" width="123px" sortable="true"><spring:message code="entity.createTime"/></th>
				<th field="updateBy" width="100px" sortable="true"><spring:message code="entity.updateBy"/></th>
				<th field="updateTime" width="123px" sortable="true"><spring:message code="entity.updateTime"/></th>
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
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDgRow('dg','${ctx}/volun/exchange/del')">
		<spring:message code="op.delBtn"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start" plain="true" onclick="processEx('canEx')">
		开启兑换
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop" plain="true" onclick="processEx('stopEx')">
		停止兑换
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
			<label style="float:left;">名字</label>
			<input name="name" class="easyui-textbox easyui-validatebox" required="true" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">总数</label>
			<input name="totalNum" class="easyui-numberspinner" data-options="min:1,max:99999" style="width: 200px"></input>
		</div>
		<div class="fitem">
			<label style="float:left;">所需积分</label>
			<input name="needIntegral" class="easyui-numberspinner" data-options="min:1,max:99999" style="width: 200px"></input>
		</div>
		<div class="fitem">
			<label style="float:left;">描述</label>
			<input name="content"  class="easyui-textbox" data-options="multiline:true" style="width:200px;height:100px;"></input>
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
