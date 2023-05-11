package com.zyg.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mac
 * @date : 2023-5-10
 */
@ApiModel(value = "",description = "")
@TableName("optimistic_lock")
public class OptimisticLock implements Serializable,Cloneable{
    /** 产品id */
    @ApiModelProperty(name = "产品id",notes = "")
    @TableId
    private int productId ;
    /** 产品数量 */
    @ApiModelProperty(name = "产品数量",notes = "")
    private int productNum ;
    /** 版本号 */
    @ApiModelProperty(name = "版本号",notes = "")
    private String updateVersion ;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }
}