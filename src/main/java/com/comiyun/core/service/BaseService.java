package com.comiyun.core.service;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

public abstract class BaseService<E> extends GenericService<E, Long> {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public boolean exist(String tableName, String columnName, String columnValue, Long id) {
        StringBuilder sb = new StringBuilder("select COUNT(1)!=0 as exist from ");
        sb.append(tableName).append(" where ").append(columnName)
                .append("='").append(columnValue).append("' and id!='").append(id).append("'");
        boolean exist = jdbcTemplate.queryForObject(sb.toString(), Boolean.class);
        return exist;
    }
}

