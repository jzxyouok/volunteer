<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/view/includes/jspTags.jsp"%>
<<script type="text/javascript">
//保存管理用户角色
function saveUserRole() {
	var userId = $("#eurForm input[name='id']").val();
	var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
	var ids = [];
	for (var i = 0; i < nodes.length; i++) {
		ids.push(nodes[i].id);
	}
	$.post('${ctx}/system/sysuserrole/editUserRole', {
		userId : userId,
		ids : ids.join(',')
	}, function(result) {
    	if(true) {
    		$('#editUserRole').dialog('close');
            $('#dg').datagrid('reload');
            $('#dg').datagrid('clearSelections');
            $.messager.show({
	                title:'<spring:message code="msg.info"/>',
	                msg:result.msg
	        	});
    	}
	}, 'json');
}
$(function(){
$('#tree').tree({
    url:"${ctx}/system/sysrole/getAllRole",
    checkbox : true,
    onLoadSuccess : function(node, data) {
    	$.post("${ctx}/system/sysuserrole/userRoleByUserId", {
    		userId : $("#eurForm input[name='id']").val()
    	}, function(result) {
    		if (result) {
    			for (var i = 0; i < result.length; i++) {
    				var node = $('#tree').tree('find', result[i].roleId);
    				if (node) {
    					$('#tree').tree('check', node.target);
    				}
    			}
    		}
    	}, 'json');
    	}
    });
});
</script>
<!-- 表单 -->
<div id="editUserRole" class="easyui-dialog" modal='true' style="width:250px;height:300px;padding:10px 20px"
            closed="true" buttons="#eur-buttons">
<form id="eurForm">
        <input type="hidden" name="id"/>
		<ul id="tree"></ul>
</form>
</div>
<div id="eur-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUserRole()">
		<spring:message code="op.saveBtn"/></a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editUserRole').dialog('close')">
		<spring:message code="op.cancelBtn"/>
	</a>
</div>