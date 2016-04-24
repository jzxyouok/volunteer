package com.comiyun.core.persistence;

/**
 * mybatis数据层接口泛型，指定主键为Long型
 *
 * @param <E> 要操作的数据表对应的实体类
 * @author ydwcn
 * @ClassName: LongPKBaseMapper
 * @date 2014-6-18 下午2:20:16
 */
public interface BaseMapper<E> extends GenericMapper<E, Long> {
}
