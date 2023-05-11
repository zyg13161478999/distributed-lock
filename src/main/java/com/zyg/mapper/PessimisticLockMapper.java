package com.zyg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyg.entity.PessimisticLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author .gang
 * @date 2023/5/10
 */
@Mapper
public interface PessimisticLockMapper extends BaseMapper<PessimisticLock> {


    @Select({
            "select lock_key from pessimistic_lock where lock_key = #{key} for update"
    })
    String query(@Param("key") String key);
}
