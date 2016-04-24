package com.comiyun.core.mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ydwcn
 * @ClassName: PageInterceptor
 * @date 2014年7月11日 上午10:52:18
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageInterceptor implements Interceptor {
    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);

    //数据库方言
    private String dialect = "mysql";

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) args[2];
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        } else {
            //忽略RowBounds-否则会进行Mybatis自带的内存分页
            args[2] = RowBounds.DEFAULT;
            MappedStatement ms = (MappedStatement) args[0];
            Object parameterObject = args[1];
            BoundSql boundSql = ms.getBoundSql(parameterObject);

            //分页信息
            PageList page = new PageList();
            MappedStatement qs = newMappedStatement(ms, new BoundSqlSqlSource(boundSql));
            //将参数中的MappedStatement替换为新的qs，防止并发异常
            args[0] = qs;
            MetaObject msObject = SystemMetaObject.forObject(qs);
            String sql = (String) msObject.getValue("sqlSource.boundSql.sql");

            //简单的通过total的值来判断是否进行count查询
            //求count - 重写sql
            msObject.setValue("sqlSource.boundSql.sql", getCountSql(sql));
            //查询总数
            Object result = invocation.proceed();
            int totalCount = (Integer) ((List) result).get(0);
            page.setTotal(totalCount);

            //分页sql - 重写sql
            msObject.setValue("sqlSource.boundSql.sql", getPageSql(sql, rowBounds));
            //恢复类型
            msObject.setValue("resultMaps", ms.getResultMaps());
            //执行分页查询
            result = invocation.proceed();
            //得到处理结果
            page.addAll((List) result);
            //返回结果
            return page;
        }
    }

    /**
     * 获取总数sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        return "select count(0) from (" + sql + ") tmp_count";
    }

    /**
     * 获取分页sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql
     * @param page
     * @return
     */
    private String getPageSql(String sql, RowBounds rowBounds) {
        StringBuilder pageSql = new StringBuilder(200);
        if ("mysql".equals(dialect)) {
            pageSql.append(sql);
            pageSql.append(" limit " + rowBounds.getOffset() + "," + rowBounds.getLimit());
        } else if ("hsqldb".equals(dialect)) {
            pageSql.append(sql);
            pageSql.append(" LIMIT " + rowBounds.getOffset() + " OFFSET " + rowBounds.getLimit());
        } else if ("oracle".equals(dialect)) {
            pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
            pageSql.append(sql);
            pageSql.append(" ) temp where rownum <= ").append(rowBounds.getLimit() + rowBounds.getOffset());
            pageSql.append(") where row_id > ").append(rowBounds.getOffset());
        }
        return pageSql.toString();
    }

    private class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    /**
     * 由于MappedStatement是一个全局共享的对象，因而需要复制一个对象来进行操作，防止并发访问导致错误
     *
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "_分页", newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuffer keyProperties = new StringBuffer();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //由于resultMaps第一次需要返回int类型的结果，所以这里需要生成一个resultMap - 防止并发错误
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), int.class, EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 只拦截Executor
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (dialect == null || dialect.equals("")) {
            throw new RuntimeException("Mybatis分页插件PageHelper无法获取dialect参数!");
        }
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }
}
