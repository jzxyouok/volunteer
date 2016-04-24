/**
 * 从多行中解析主键以 , 分隔
 * @param rows
 * @returns {String}
 */
function resolveIds(rows) {
	var ids = "";
	if (rows && rows.length >= 1) {
		for (var i = 0; i < rows.length; i++) {
			var id = rows[i].id;
			if (i == 0) {
				ids = id;
			} else {
				ids = ids + "," + id;
			}
		}
	}
	return ids;
}

/**
 * 格式化DataGrid里面的时间字符串
 * @param value
 * @param row
 * @param index
 * @returns
 */
function fmtTime(value,row,index){
	if(value==null || value=='') {
		return '';
	}
	var unixTimestamp = new Date(value);
	return unixTimestamp.format("yyyy-MM-dd hh:mm:ss");
}

/**
 * 格式化DataGrid里面的时间字符串
 * @param value
 * @param row
 * @param index
 * @returns
 */
function fmtDate(value,row,index){ 
	var unixTimestamp = new Date(value);
	return unixTimestamp.format("yyyy-MM-dd");
}
