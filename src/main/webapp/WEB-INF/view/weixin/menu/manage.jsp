<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	//打开新增表单
	function add() {
		$('#pidCombo').combobox('reload');
	    $('#dlg').dialog('open').dialog('setTitle','新增微信菜单');
	    $('#form').form('clear');
	    url = '${ctx}/weixin/menu/add';
	}
	
	//打开编辑表单
	function edit() {
		$('#pidCombo').combobox('reload');
        var rows = $('#dg').datagrid('getSelections');
        if(rows.length==1) {
        	var row = rows[0];
        	$('#dlg').dialog('open').dialog('setTitle','编辑微信菜单');
        	resetItem(row.type);
            $('#form').form('load',row);
            url = '${ctx}/weixin/menu/update';
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
                    $('#dg').treegrid('reload');
                    $('#dg').treegrid('clearSelections');
            	}
            }
        });
    }
	
	/**同步到微信*/
	function sync() {
		$.get('${ctx}/weixin/menu/sync',function(data){
			$.messager.show({
                title:'<spring:message code="msg.info"/>',
                msg:data.msg
        	});
        });
	}
	
	/**菜单类型变更*/
	function typeSelect(rec) {
		var type = rec.key;
		resetItem(type);
	}
	
	function resetItem(type) {
		if(type=='view') {
			$('#mkey').textbox('disable');
			$('#murl').textbox('enable');
			$('#mmetiaId').textbox('disable');
			$('#extendId').textbox('disable');
		} else if(type=='click' || type=='scancode_push' || type=='scancode_waitmsg' ) {
			$('#mkey').textbox('enable');
			$('#murl').textbox('disable');
			$('#mmetiaId').textbox('disable');
			$('#extendId').textbox('disable');
		} else if(type=='media_id' || type=='view_limited' ) {
			$('#mkey').textbox('disable');
			$('#murl').textbox('disable');
			$('#mmetiaId').textbox('enable');
			$('#extendId').textbox('disable');
		} else if(type=='extend') {
			$('#mkey').textbox('enable');
			$('#murl').textbox('disable');
			$('#mmetiaId').textbox('disable');
			$('#extendId').textbox('enable');
		}
	}
	
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-treegrid" style="width:750px;margin:20px 0;" data-options="
                url: '${ctx}/weixin/menu/list',
                method: 'get',
                fit:true,
                border:false,
                rownumbers: true,
                idField: 'id',
                treeField: 'name'
            ">
		<thead>
            <tr>
                <th data-options="field:'name'" width="160">菜单名字</th>
                <th data-options="field:'typeDesc'" width="80">菜单类型</th>
                <th data-options="field:'key'" width="110">菜单KEY</th>
                <th data-options="field:'url'" width="175">URL</th>
                <th data-options="field:'metiaId'" width="175">素材</th>
                <th data-options="field:'extendName'" width="175">扩展实现</th>
                <th data-options="field:'sn'" width="50">排序号</th>
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
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delTreeDgRow('dg','${ctx}/weixin/menu/del')">
		<spring:message code="op.delBtn"/>
	</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="sync()">
		同步到微信
	</a>
</div>

<!-- 表单窗口 -->
<div id="dlg" class="easyui-dialog" modal='true' style="padding:10px 20px"
            closed="true" buttons="#dlg_buttons">
    <!-- 表单 -->
	<form id="form" method="post" novalidate>
		<input type="hidden" name="id"/>
		<div class="fitem">
			<label style="float:left;">菜单名字</label>
			<input name="name" class="easyui-textbox easyui-validatebox" required="true" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">父级菜单</label>
			<input id="pidCombo" name="pid" class="easyui-combobox" required="true" 
				data-options="valueField:'id',textField:'name',editable:false,url:'/weixin/menu/plist'" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">菜单类型</label>
			<input name="type" class="easyui-combobox" required="true" 
				data-options="valueField:'key',textField:'name',editable:false,url:'/weixin/menu/menuTypeList',onSelect:typeSelect" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">菜单KEY</label>
			<input id="mkey" name="key" class="easyui-textbox easyui-validatebox" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">URL</label>
			<input id="murl" name="url" class="easyui-textbox easyui-validatebox" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">素材ID</label>
			<input id="mmetiaId" name="metiaId" class="easyui-textbox easyui-validatebox" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">扩展实现</label>
			<input id="extendId" name="extendId" class="easyui-combobox"
				data-options="valueField:'id',textField:'name',editable:false,url:'/weixin/extend/list'" style="width: 200px">
		</div>
		<div class="fitem">
			<label style="float:left;">顺序号</label>
			<input name="sn" class="easyui-numberspinner" data-options="min:1,max:99" style="width: 200px"></input>
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
