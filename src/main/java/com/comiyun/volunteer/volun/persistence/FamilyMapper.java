package com.comiyun.volunteer.volun.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.volun.entity.Family;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 家庭管理
 *
 * @author david
 */
public interface FamilyMapper extends BaseMapper<Family> {

    /**
     * 获取当前code
     *
     * @return
     */
    public Long currFamilyCode();

    public List<Family> queryByCode(@Param("code") String code);

}
