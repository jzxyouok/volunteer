package com.comiyun.volunteer.volun.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.volun.entity.ActivityPersion;
import com.comiyun.volunteer.volun.enums.ActivityPersionStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityPersionMapper extends BaseMapper<ActivityPersion> {

    void changestatus(@Param("id") Long id, @Param("status") ActivityPersionStatus status);

    public List<ActivityPersion> queryByActIdAndPerId(@Param("actId") Long actId, @Param("perId") Long perId);

    public List<ActivityPersion> queryByActId(@Param("actId") Long actId);

}
