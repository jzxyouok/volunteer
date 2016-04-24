/**
 * Javascript扩展
 */

/**对Date的扩展，将 Date 转化为指定格式的String   
 *月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
 *年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
 *例子：   
 *(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
 *(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //月份   
		"d+" : this.getDate(), //日   
		"h+" : this.getHours(), //小时   
		"m+" : this.getMinutes(), //分   
		"s+" : this.getSeconds(), //秒   
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度   
		"S" : this.getMilliseconds()
	//毫秒   
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

/**
 * @requires jQuery
 * 
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
$.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

$(document).ajaxError(function(event,xhr,options) {
	if(xhr.readyState==4) {
		var data = $.parseJSON(xhr.responseText);
		if(data!=null && data.hasOwnProperty('code') 
				&& data.hasOwnProperty('success') && data.hasOwnProperty('msg')) {
			if('login_timeout'==data.code) {
				$.messager.confirm('警告', data.msg, function(r){
		            if (r){
		            	top.location.href=_ctx + "/login";
		            }
		        });
				xhr.abort();
			} else if('system_exception'==data.code){
				$.messager.show({
	                title:'警告',
	                msg:data.msg
	            });
			}
		}
	}
});
