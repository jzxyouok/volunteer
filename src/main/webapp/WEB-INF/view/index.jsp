<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/includes/jspHead.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="os.softName"/></title>
<script type="text/javascript">

	$(function(){
		var menuTree = $('#menuTree');
		var contentTabs = $('#contentTabs');
		contentTabs.tabs('add',{
	        title: '首页',
	        icon: 'icon-index',
	        content :'<img style="width:850px;height: 450px;" src="styles/images/mainbg.jpg" />', 
	        tabWidth:'80',
	        closable: false
	    });
		//系统菜单
		menuTree.tree({
			url : "${ctx}/system/sysmenu/tree",
			parentField : 'pid',
			onClick : function(node) {
				if(node.isLeaf) {
					if (node.url) {
						var url = _ctx + node.url;
						//已存在
						if(contentTabs.tabs('exists', node.text)) {
							contentTabs.tabs('select', node.text);
						} else {
							contentTabs.tabs('add',{
						        title: node.text,
						        icon: node.iconCls,
						        content: '<iframe scrolling="no" frameborder="0" marginheight="0" marginwidth="0" src="'+url+'" style="width:100%;height:100%;"></iframe>',
						        closable: true
						    });
						}
					} else {
						$.messager.alert('<spring:message code="msg.info"/>','<spring:message code="msg.noMenuUrl"/>','warning');
					}
				} else {
// 					打开关闭父节点
					if(node.state=='closed') {
						menuTree.tree('expand',node.target);  
					} else {
						menuTree.tree('collapse',node.target);  
					}
				}
			}
		});
	});
	
	function logout() {
		 $.messager.confirm('<spring:message code="logout.title"/>', '<spring:message code="logout.message"/>', function(r){
             if (r){
            	 location.href=_ctx + "/logout";
             }
         });
	}
	
	function changepwddlg() {
		$('#pwddlg').dialog('open').dialog('setTitle','修改密码');
	}
	
	function changepwd() {
		var oldpwdv = $('#oldpwd').val();
		var newpwdv = $('#newpwd').val();
		$.post('${ctx}/system/sysuser/changePwd',{oldPwd:oldpwdv,newPwd:newpwdv},function(result){
            if (result.success){
           	 $.messager.show({
	                title:'<spring:message code="msg.info"/>',
	                msg:result.msg
	             });
           	$('#pwddlg').dialog('close');
            } else {
                $.messager.show({
                    title: 'Error',
                    msg: result.msg
                });
            }
        },'json');
		
	}
</script>
</head>
<body class="easyui-layout">
	<!-- North -->
	<div data-options="region:'north',border:false" class="headerbg">
		<div class="left_logo">
			<img style="width: 155px;height: 60px;" src="styles/images/logo.jpg" />
		</div>
		<div style="position: absolute; right: 15px; bottom: 15px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-curruser" data-options="plain:true"><shiro:principal/></a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="chnagepwd" data-options="plain:true" onclick="changepwddlg()">修改密码</a>
			<a href="javascript:logout()" class="easyui-linkbutton" iconCls="icon-logout" data-options="plain:true"><spring:message code="main.logoutBtn"/></a>
		</div>
	</div>
	<!-- West -->
	<div data-options="region:'west',split:true,title:'<spring:message code="main.nav"/>'" style="width:150px;">
		<ul id="menuTree"></ul>
	</div>
	<!-- Center -->
	<div data-options="region:'center'" style="overflow: hidden;">
		<div id="contentTabs" class="easyui-tabs" data-options="fit:true,border:false">
		</div>
	</div>
	<!-- Footer -->
<!-- 	<div data-options="region:'south',border:false" style="height: 30px; line-height: 30px; font-family: Arial; text-align: center;"> -->
<!-- 		<spring:message code="os.copyright"/> -->
<!-- 	</div> -->

<!-- 表单窗口 -->
<div id="pwddlg" class="easyui-dialog" modal='true' style="padding:10px 20px"
            closed="true" buttons="#pwddlg_buttons">
    <!-- 表单 -->
	<div class="fitem">
		<label>旧密码</label>
		<input id=oldpwd class="easyui-textbox">
	</div>
	<div class="fitem">
		<label>新密码</label>
		<input id=newpwd class="easyui-textbox">
	</div>
	<div id="pwddlg_buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="changepwd()">
			<spring:message code="op.saveBtn"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#pwddlg').dialog('close')">
			<spring:message code="op.cancelBtn"/>
		</a>
	</div>
</div>
</body>
</html>