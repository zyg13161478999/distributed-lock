package com.zyg.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * ;
 * @date : 2023-5-10
 */
@ApiModel(value = "",description = "")
@TableName("pessimistic_lock")
public class PessimisticLock implements Serializable,Cloneable{
    /** 锁名称 */
    @ApiModelProperty(name = "锁名称",notes = "")
    @TableId
    private String lockKey ;


    /** 锁名称 */
    public String getLockKey(){
        return this.lockKey;
    }
    /** 锁名称 */
    public void setLockKey(String lockKey){
        this.lockKey=lockKey;
    }


}