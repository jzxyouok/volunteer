<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	//打开新增表单
	function ex() {
		$('#pidCombo').combobox('reload');
		$('#form').form('clear');
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','兑换');
            $('#form').form('load',row);
            url = '${ctx}/volun/exchangedetail/edit';
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
	
	function cancel() {
		$('#pidCombo').combobox('reload');
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','编辑活动');
            $('#form').form('load',row);
            url = '${ctx}/volun/exchangedetail/edit';
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
            url: '${ctx}/volun/exchangedetail/ex',
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
	
	function digitStyle(value,row,index) {
		if (value < 0) {
            return 'color:red;';
        } else {
        	 return 'color:green;';
        }
	}
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="width:750px;margin:20px 0;" data-options="
                url: '${ctx}/volun/exchangedetail/list',
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
                <th data-options="field:'exName'" width="130" sortable="true">兑换物品</th>
                <th data-options="field:'persionName'" width="70" sortable="true">义工姓名</th>
                <th data-options="field:'persionMobile'" width="85" sortable="true">电话</th>
                <th data-options="field:'persionSexDesc'" width="40">性别</th>
                <th data-options="field:'num'" width="65" sortable="true">兑换数量</th>
                <th data-options="field:'perIntegral'" width="65" sortable="true">单价</th>
				<th field="createBy" width="100px" sortable="true"><spring:message code="entity.createBy"/></th>
				<th field="createTime" width="123px" sortable="true"><spring:message code="entity.createTime"/></th>
                <th data-options="field:'statusDesc'" width="80">状态</th>
            </tr>
        </thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="ex()">
		兑换
	</a>
<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="cancel()"> -->
<!-- 		取消 -->
<!-- 	</a> -->
	<div>
	<form id="searchForm">
		兑换物品<input name="exName" class="easyui-textbox">
		义工姓名<input name="persionName" class="easyui-textbox">
		义工来源
		<select  name="status" data-options="editable:false,panelHeight:'auto'" class="easyui-combobox" style="width:100px;">
		    <option value="">==请选择==</option>
		    <option value="unsend">未发送</option>
		    <option value="sended">已发送</option>
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
			<label style="float:left;">兑换物品</label>
			<input name="exName" class="easyui-textbox" data-options="disabled:true" style="width: 150px">
		</div>
		<div class="fitem">
			<label style="float:left;">义工姓名</label>
			<input name="persionName" class="easyui-textbox" data-options="disabled:true" style="width: 150px">
		</div>
		<div class="fitem">
			<label style="float:left;">单价</label>
			<input name="perIntegral" class="easyui-textbox" data-options="disabled:true" style="width: 150px">
		</div>
		<div class="fitem">
			<label style="float:left;">数量</label>
			<input name="num" class="easyui-textbox" data-options="disabled:true" style="width: 150px">
		</div>
		<div class="fitem">
			<label style="float:left;">兑换码</label>
			<input name="code" class="easyui-textbox" style="width: 150px">
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
