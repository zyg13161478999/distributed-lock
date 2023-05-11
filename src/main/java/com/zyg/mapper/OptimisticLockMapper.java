package com.zyg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyg.entity.OptimisticLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author .gang
 * @date 2023/5/10
 */
@Mapper
public interface OptimisticLockMapper extends BaseMapper<OptimisticLock> {

    @Update({
            "update optimistic_lock ",
            "set product_num = product_num -1,",
            "update_version = #{nuwUpdateVersion} ",
            "where product_id = #{productId} and update_version = #{updateVersion}"
    })
    int updateProductNum(@Param("productId") int productId,
                         @Param("updateVersion") String updateVersion,
                         @Param("nuwUpdateVersion") String nuwUpdateVersion);
}
