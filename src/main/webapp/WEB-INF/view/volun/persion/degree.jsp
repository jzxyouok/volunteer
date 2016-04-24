<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="margin:20px 0;" data-options="
                url: '${ctx}/volun/persion/degreelist',
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
                <th data-options="field:'degree'" width="85" sortable="true">贡献度</th>
                <th data-options="field:'degree'" width="85" sortable="true">星级</th>
            </tr>
        </thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<div>
	<form id="searchForm">
		姓名 <input name="name" class="easyui-textbox" style="width: 150px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchForm('dg','searchForm')"><spring:message code="op.queryBtn"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="reSearchForm('dg','searchForm')"><spring:message code="op.resetBtn"/></a>
	</form>
    </div>
</div>
</div>
