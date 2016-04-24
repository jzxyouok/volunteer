<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<script type="text/javascript">
	
</script>
<!-- 列表 -->
<table id="dg" toolbar="#searchBar" class="easyui-datagrid" style="width:750px;margin:20px 0;" data-options="
                url: '${ctx}/volun/persion/birthlist',
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
                <th data-options="field:'areaName'" width="120" sortable="true">所属社区</th>
                <th data-options="field:'statusDesc'" width="80">状态</th>
            </tr>
        </thead>
</table>

<!-- 工具条 -->
<div id="searchBar" style="padding: 5px">
	<div>
	<form id="searchForm">
		自 <input name="startBirthday" class="easyui-datebox" data-options="editable:false">
		起,未来
		<input name="days" class="easyui-numberspinner" value="30" data-options="min:1,max:999" style="width: 70px"></input>
		天内提醒
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchForm('dg','searchForm')"><spring:message code="op.queryBtn"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="reSearchForm('dg','searchForm')"><spring:message code="op.resetBtn"/></a>
	</form>
    </div>
</div>
</div>
