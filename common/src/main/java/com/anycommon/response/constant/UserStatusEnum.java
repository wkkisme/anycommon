package com.anycommon.response.constant;

/**
 * 用户状态
 *
 * @author mac
 */

public enum UserStatusEnum {

    /**
     * 在线
     */

    ONLINE(0),

    /**
     * 离线
     */
    OFFLINE(1),

    /**
     * 不可被邀请
     */
    CANT_BE_INVITE(2),
    ;

    private final Integer status;

    UserStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
