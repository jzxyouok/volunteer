package com.comiyun.core.web.json;

import com.comiyun.core.web.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ydwcn
 * @ClassName: QueryFilter
 * @date 2014年7月7日 下午3:59:36
 */
public class QueryFilter implements Serializable {
    private static final long serialVersionUID = -3894266496237763631L;
    private Integer page;// 当前页
    private Integer rows;// 每页显示记录数
    private String sort;// 排序字段
    private String order;// ASC/DESC
    private Map<String, Object> params = new HashMap<String, Object>(); //查询条件

    public QueryFilter(HttpServletRequest request) {
        page = RequestUtil.getInt(request, "page");
        rows = RequestUtil.getInt(request, "rows");
        sort = RequestUtil.getString(request, "sort");
        order = RequestUtil.getString(request, "order");
        //排序这符串
        String orderStr = genOrderStr(sort, order);
        params.put("orderField", orderStr);

        Map<String, String[]> p = request.getParameterMap();
        Object[] keys = p.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i].toString();
            if ("page".equals(key) || "rows".equals(key)
                    || "sort".equals(key) || "order".equals(key)) {
                continue;
            }
            String[] vs = p.get(key);
            if (vs != null && vs.length != 0) {
                String value = vs[0];
                if (StringUtils.isNotBlank(value)) {
                    params.put(key, value);
                }
            }
        }
    }

    public RowBounds getRowBounds() {
        if (page == null || rows == null) {
            return null;
        }
        int index = page > 0 ? (page - 1) * rows : 0;
        RowBounds rowBounds = new RowBounds(index, rows);
        return rowBounds;
    }

    /**
     * 生成排序段
     *
     * @param fields
     * @param orders
     * @return String
     * @throws
     * @Title: genOrderStr
     */
    private String genOrderStr(String fields, String orders) {
        if (StringUtils.isNotBlank(fields) && StringUtils.isNotBlank(orders)) {
            String[] fieldArray = fields.split(",");
            String[] orderArray = orders.split(",");
            if (fieldArray.length == orderArray.length) {
                StringBuffer sb = new StringBuffer("");
                for (int i = 0; i < fieldArray.length; i++) {
                    String field = fieldArray[i];
                    String order = orderArray[i];
                    if (i == 0) {
                        sb.append(field)
                                .append(" ")
                                .append(order);
                    } else {
                        sb.append(",")
                                .append(field)
                                .append(" ")
                                .append(order);
                    }
                }
                return sb.toString();
            } else {
                return null;
            }
        }
        return null;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
