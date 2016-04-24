<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<!-- logo -->
<link rel="icon" href="<c:url value="/styles/favicon.ico" />" type="image/x-icon" />

<!-- 项目根路径 -->
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/themes/icon.css">

<script type="text/javascript" src="${ctx}/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/plugins/utils/extJs.js"></script>
<script type="text/javascript" src="${ctx}/plugins/utils/exEasyUI.js"></script>
<script type="text/javascript" src="${ctx}/plugins/utils/Common.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/styles/icons.css">
<link rel="stylesheet" type="text/css" href="${ctx}/styles/style.css">

<script type="text/javascript">
		var _ctx = "<%= request.getContextPath() %>";
		/**查询函数*/
		function searchForm(datagrid,searchForm) {
			$("#" + datagrid).datagrid('load', $.serializeObject($("#" + searchForm)));
			$("#" + datagrid).datagrid('clearSelections');
		}

		/**重置函数*/
		function reSearchForm(datagrid,searchForm) {
			$("#" + searchForm).form('clear');
			$("#" + datagrid).datagrid('load', $.serializeObject($("#" + searchForm)));
			$("#" + datagrid).datagrid('clearSelections');
		}
		
		
		/**删除表格行*/
		function delDgRow(datagrid,url){
	         var rows = $("#" + datagrid).datagrid('getSelections');
	         if (rows.length>=1){
	             $.messager.confirm('<spring:message code="msg.confirm"/>','<spring:message code="msg.sureDelRows"/>',function(r){
	                 if (r){
	                     $.post(url,{ids:resolveIds(rows)},function(result){
	                         if (result.success){
	                        	 $("#" + datagrid).datagrid('reload');
	                        	 $("#" + datagrid).datagrid('clearSelections');
	                        	 $.messager.show({
	     			                title:'<spring:message code="msg.info"/>',
	     			                msg:'<spring:message code="msg.delsuccess"/>'
	     			             });
	                         } else {
	                             $.messager.show({
	                                 title: 'Error',
	                                 msg: result.msg
	                             });
	                         }
	                     },'json');
	                 }
	             });
			} else {
	        	 $.messager.show({
	                 title:'<spring:message code="msg.warn"/>',
	                 msg:'<spring:message code="msg.noSelectRow"/>'
	         	});
			}
		}
		
		/**删除表格行*/
		function delTreeDgRow(datagrid,url){
		     var rows = $("#" + datagrid).datagrid('getSelections');
		     if (rows.length>=1){
		         $.messager.confirm('<spring:message code="msg.confirm"/>','<spring:message code="msg.sureDelRows"/>',function(r){
		             if (r){
		                 $.post(url,{ids:resolveIds(rows)},function(result){
		                     if (true){
		                    	 $("#" + datagrid).treegrid('reload');
		                    	 $("#" + datagrid).treegrid('clearSelections');
		                    	 $.messager.show({
		 			                title:'<spring:message code="msg.info"/>',
		 			                msg:'<spring:message code="msg.delsuccess"/>'
		 			             });
		                     } else {
		                         $.messager.show({
		                             title: 'Error',
		                             msg: result.errorMsg
		                         });
		                     }
		                 },'json');
		             }
		         });
			} else {
		    	 $.messager.show({
		             title:'<spring:message code="msg.warn"/>',
		             msg:'<spring:message code="msg.noSelectRow"/>'
		     	});
			}
		}
		
		function digitStyle(value,row,index) {
			if (value < 0) {
	            return 'color:red;';
	        } else {
	        	 return 'color:green;';
	        }
		}
		
		function qrcodeUrl(value,row,index){
			if(value!=null && value!="" && value != undefined) {
				return "<a target='_blank' href='${ctx}/weixin/qrcode/get?id="+value+"'>查看二维码</a>";
			} else {
				return "";
			}
		}
</script>