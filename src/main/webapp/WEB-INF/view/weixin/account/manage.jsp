<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
</script>
<!-- 列表 -->
<table id="wxaccount_pg" toolbar="#wxaccount_searchBar" class="easyui-propertygrid" style="width:550px;margin:20px 0;" data-options="
                url:'${ctx}/weixin/account/info',
                method:'get',
                showGroup:false,
                scrollbarSize:0,
                border:false,
                columns:wxaccount_columns
            ">
</table>

<!-- 工具条 -->
<div id="wxaccount_searchBar" style="padding: 5px">
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveWxAccount()">
		<spring:message code="op.saveBtn"/>
	</a>
</div>

<script>
        var wxaccount_columns = [[
       			{field:'name',title:'属性名',width:200,sortable:false},
        		{field:'value',title:'属性值',width:350,resizable:false}
        ]];
        
        /**保存微信账号*/
        function saveWxAccount() {
        	var params = {};
        	var rows = $('#wxaccount_pg').propertygrid('getChanges');
            for(var i=0; i<rows.length; i++){
            	params[rows[i].key] = rows[i].value;
            }
        	$.post('${ctx}/weixin/account/update',params,function(result){
	   			$.messager.show({
	   	                title:'<spring:message code="msg.info"/>',
	   	                msg:result.msg
	   	             });
	   		 $('#wxaccount_pg').propertygrid('reload');
            },'json');
        }
</script>

