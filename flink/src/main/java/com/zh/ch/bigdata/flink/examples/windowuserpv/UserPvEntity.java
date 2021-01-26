package com.zh.ch.bigdata.flink.examples.windowuserpv;

/**
 * @author xzc
 * @description 实体类
 * @date 2021/01/04
 */
public class UserPvEntity {

    private String time;

    private String userId;

    private Long pvCount;

    public UserPvEntity(){}

    public UserPvEntity(String time, String userId, Long pvCount) {
        this.time = time;
        this.userId = userId;
        this.pvCount = pvCount;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPvCount(Long pvCount) {
        this.pvCount = pvCount;
    }

    public String getTime() {
        return time;
    }

    public String getUserId() {
        return userId;
    }

    public Long getPvCount() {
        return pvCount;
    }
}
