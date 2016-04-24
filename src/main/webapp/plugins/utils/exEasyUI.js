var ex = ex || {};

/**
 * 防止panel/window/dialog组件超出浏览器边界
 */
ex.onMove = {
	onMove : function(left, top) {
		var l = left;
		var t = top;
		if (l < 1) {
			l = 1;
		}
		if (t < 1) {
			t = 1;
		}
		var width = parseInt($(this).parent().css('width')) + 14;
		var height = parseInt($(this).parent().css('height')) + 14;
		var right = l + width;
		var buttom = t + height;
		var browserWidth = $(window).width();
		var browserHeight = $(window).height();
		if (right > browserWidth) {
			l = browserWidth - width;
		}
		if (buttom > browserHeight) {
			t = browserHeight - height;
		}
		$(this).parent().css({/* 修正面板位置 */
			left : l,
			top : t
		});
	}
};
$.extend($.fn.dialog.defaults, ex.onMove);
$.extend($.fn.window.defaults, ex.onMove);
$.extend($.fn.panel.defaults, ex.onMove);

/**
 * 
 * 通用错误提示
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 * @requires jQuery,EasyUI
 */
//ex.onLoadError = {
//	onLoadError : function(XMLHttpRequest) {
//		if (parent.$ && parent.$.messager) {
//			parent.$.messager.progress('close');
//			parent.$.messager.alert('错误', XMLHttpRequest.responseText);
//		} else {
//			$.messager.progress('close');
//			$.messager.alert('错误', XMLHttpRequest.responseText);
//		}
//	}
//};
//$.extend($.fn.datagrid.defaults, ex.onLoadError);
//$.extend($.fn.treegrid.defaults, ex.onLoadError);
//$.extend($.fn.tree.defaults, ex.onLoadError);
//$.extend($.fn.combogrid.defaults, ex.onLoadError);
//$.extend($.fn.combobox.defaults, ex.onLoadError);
//$.extend($.fn.form.defaults, ex.onLoadError);
//$.extend($.fn.panel.defaults, ex.onLoadError);
