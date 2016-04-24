package com.comiyun.core.service;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.web.json.QueryFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Service层泛型基类，对Service层通用方法进行统一封装
 *
 * @param <E>  要操作的数据表对应的实体类
 * @param <PK> 表的主键类型
 * @author wanghongwei
 * @since v2.0 2014-04-02
 */
public abstract class GenericService<E, PK extends Serializable> {
    protected Logger logger = null;

    public GenericService() {
        logger = LoggerFactory.getLogger(getClass().getName());
    }

    /**
     * 获取数层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    protected abstract GenericMapper<E, PK> getMapper();

    /**
     * 增加记录
     *
     * @param entity E 要增加的记录对象
     * @return int 受影响的记录条数
     */
    public int insert(E entity) throws ServiceException {
        return getMapper().insert(entity);
    }

    /**
     * 以主健删除记录
     *
     * @param id PK 要删除的记录主健
     * @return int 受影响的记录条数
     */
    public int delete(PK id) throws ServiceException {
        return getMapper().delete(id);
    }

    /**
     * 以主健批量伤处记录
     *
     * @param primaryKeys List<PK> 要删除的记录主健列表
     * @return int 受影响的记录条数
     */
    public int batchDelete(List<PK> primaryKeys) throws ServiceException {
        return getMapper().batchDelete(primaryKeys);
    }

    /**
     * 更新记录
     *
     * @param entity E 要更新的记录对象
     * @return int 受影响的记录条数
     */
    public int update(E entity) throws ServiceException {
        return getMapper().update(entity);
    }

    /**
     * 根据主健查找记录
     *
     * @param primaryKey PK 主健值
     * @return E 查找的记录对象
     */
    public E get(PK primaryKey) {
        return getMapper().get(primaryKey);
    }

    /**
     * 获取符合条件的所有记录
     *
     * @return List<E> 符合条件的记录列表
     */
    public List<E> getList() {
        return getMapper().getList();
    }

    /**
     * 获取符合条件的所有记录
     *
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<E> 符合条件的记录列表
     */
    public List<E> getList(Map<String, Object> conditions) {
        return getMapper().getList(conditions);
    }

    /**
     * 按查询条件查询
     *
     * @param @param  queryFilter
     * @param @return
     * @return List<E>
     * @throws
     * @Title: getList
     */
    public List<E> getList(QueryFilter queryFilter) {
        List<E> list = getMapper().getList(queryFilter.getParams(), queryFilter.getRowBounds());
        //如果存在分页，则查询总条数
//		if(list instanceof PageList) {
//			PageList<E> pageList = (PageList<E>)list;
//			int count = getMapper().getListCount(queryFilter.getParams());
//			pageList.setTotal(count);
//		}
        return list;
    }
}
