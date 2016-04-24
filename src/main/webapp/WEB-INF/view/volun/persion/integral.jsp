<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	var url;
	function tjjfD() {
		var rows = $("#dg").datagrid('getSelections');
		if (rows.length>=1){
			$('#adlg').dialog('open').dialog('setTitle','添加积分');
			$('#form').form('clear');
			url = '${ctx}/volun/persion/tjjf';
		} else {
			 $.messager.show({
             title:'<spring:message code="msg.warn"/>',
             msg:'<spring:message code="msg.noSelectRow"/>'
     		});
		}
	}
	
	//扣减积分
	function kjjfD() {
		var rows = $("#dg").datagrid('getSelections');
		if (rows.length>=1){
			$('#adlg').dialog('open').dialog('setTitle','扣减积分');
			$('#form').form('clear');
			url = '${ctx}/volun/persion/kjjf';
		} else {
			 $.messager.show({
             title:'<spring:message code="msg.warn"/>',
             msg:'<spring:message code="msg.noSelectRow"/>'
     		});
		}
	}

	//添加积分
	function tjjf() {
		var rows = $("#dg").datagrid('getSelections');
		$("#tjIds").val(resolveIds(rows));
		$('#form').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
            	if(true) {
            		$('#adlg').dialog('close');
                    $('#dg').datagrid('reload');
                    $('#dg').datagrid('clearSelections');
                    $.messager.show({
			                title:'<spring:message code="msg.info"/>',
			                msg:'操作成功'
			        	});
            	}
            }
        });
	}
	
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="margin:20px 0;" data-options="
                url: '${ctx}/volun/persion/integrallist',
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
                <th data-options="field:'statusDesc'" width="80">状态</th>
                <th data-options="field:'integral',align:'right',styler:digitStyle" width="85" sortable="true">可用积分</th>
            </tr>
        </thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="tjjfD()">
		   添加积分
		</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="kjjfD()">
		   扣减积分
		</a>
	<form id="searchForm">
		姓名 <input name="name" class="easyui-textbox" style="width: 150px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchForm('dg','searchForm')"><spring:message code="op.queryBtn"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="reSearchForm('dg','searchForm')"><spring:message code="op.resetBtn"/></a>
	</form>
    </div>
</div>

<!-- 添加积分 -->
<div id="adlg" class="easyui-dialog" modal='true' style="padding:10px 20px"
            closed="true" buttons="#adlg-buttons">
	<form id="form" method="post" novalidate>
		<input type="hidden" id="tjIds" name="ids" />
		<div class="fitem">
			<label>积分</label>
			<input name="digit" class="easyui-numberspinner" data-options="min:1,max:99999"></input>
		</div>
	</form>
</div>
<div id="adlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="tjjf()">
		<spring:message code="op.saveBtn"/></a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#adlg').dialog('close')">
		<spring:message code="op.cancelBtn"/>
	</a>
</div>
