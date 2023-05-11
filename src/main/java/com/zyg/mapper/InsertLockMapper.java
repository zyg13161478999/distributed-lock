package com.zyg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyg.entity.InsertLock;
import org.apache.ibatis.annotations.*;

/**
 * @author .gang
 * @date 2023/5/10
 */
@Mapper
public interface InsertLockMapper  extends BaseMapper<InsertLock> {

    @Insert({
            "INSERT INTO insert_lock (lock_key,client_id) VALUES(#{key},#{clientId})"
    })
    int insertLock(@Param("key") String key, @Param("clientId")String clientId);

    @Delete({
            "delete from insert_lock where lock_key = #{key} and client_id = #{clientId}"
    })
    int delLock(@Param("key") String key, @Param("clientId")String clientId);
}
