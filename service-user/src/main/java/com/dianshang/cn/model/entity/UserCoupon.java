package com.dianshang.cn.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserCoupon {
    private Integer id;

    private Integer userId;

    private Integer couponCode;

    private BigDecimal couponDenomination;

    private Date createTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Integer couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getCouponDenomination() {
        return couponDenomination;
    }

    public void setCouponDenomination(BigDecimal couponDenomination) {
        this.couponDenomination = couponDenomination;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}