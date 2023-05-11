package com.zyg.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ;
 * @date : 2023-5-10
 */
@ApiModel(value = "",description = "")
@TableName("insert_lock")
public class InsertLock implements Serializable,Cloneable{
    /** 锁名称 */
    @ApiModelProperty(name = "锁名称",notes = "")
    @TableId
    private String lockKey ;
    /** 线程id */
    @ApiModelProperty(name = "线程id",notes = "")
    private String clientId ;


    /** 锁名称 */
    public String getLockKey(){
        return this.lockKey;
    }
    /** 锁名称 */
    public void setLockKey(String lockKey){
        this.lockKey=lockKey;
    }
    /** 线程id */
    public String getClientId(){
        return this.clientId;
    }
    /** 线程id */
    public void setClientId(String clientId){
        this.clientId=clientId;
    }

}