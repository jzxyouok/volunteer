package com.comiyun.core.persistence;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * mybatis数据层接口泛型基类，对数据层通用接口进行统一封装
 *
 * @param <E>  要操作的数据表对应的实体类
 * @param <PK> 表的主键类型
 * @author ydwcn
 * @ClassName: GenericMapper
 * @date 2014-6-18 下午2:19:40
 */
public interface GenericMapper<E, PK extends Serializable> {

    /**
     * 根据主键获取实体对象
     *
     * @param primaryKey PK 主键
     * @return E 实体对象
     */
    E get(PK primaryKey);

    /**
     * 根据条件查询记录列表
     *
     * @param conditions Map<String, Object> 条件map
     * @return List<E> 记录列表
     */
    List<E> getList();

    List<E> getList(Map<String, Object> conditions);

    List<E> getList(Map<String, Object> conditions, RowBounds bounds);

    int getListCount(Map<String, Object> conditions);

    /**
     * 插入新记录
     *
     * @param entity E 待插入实体对象
     * @return int
     */
    int insert(E entity);

    /**
     * 更新记录
     *
     * @param entity E 待更新实体对象
     * @return int 受影响的记录数
     */
    int update(E entity);

    /**
     * 根据主键删除记录
     *
     * @param primaryKey PK 主键值
     * @return int 删除条数
     */
    int delete(PK primaryKey);

    /**
     * 根据主键列表批量删除记录
     *
     * @param primaryKeys List<PK>主键列表
     * @return int 删除条数
     */
    int batchDelete(List<PK> primaryKeys);
}
