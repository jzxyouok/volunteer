package com.comiyun.volunteer.system.entity;

import com.comiyun.core.constant.DataType;
import com.comiyun.core.entity.BaseEntity;

/**
 * 系统参数了
 *
 * @author ydwcn
 * @ClassName: SysParam
 * @date 2014年7月8日 下午3:01:01
 */
public class SysParam extends BaseEntity {

    private static final long serialVersionUID = 7996243273049794097L;
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 参数类型
     */
    private DataType dataType;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

}
