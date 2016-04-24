package com.comiyun.core.web.json;

import com.comiyun.core.mybatis.plugin.PageList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataGrid implements Serializable {
    private static final long serialVersionUID = -1700381523078612247L;

    private Integer total;

    @SuppressWarnings("rawtypes")
    private List rows = new ArrayList();

    @SuppressWarnings("unchecked")
    public DataGrid(List<?> list) {
        if (list != null) {
            if (list instanceof PageList) {
                total = ((PageList<?>) list).getTotal();
                rows.addAll(list);
            } else {
                total = list.size();
                rows = list;
            }
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

}
