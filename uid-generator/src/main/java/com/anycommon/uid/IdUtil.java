/*
 * Copyright 2014-2020 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */

package com.anycommon.uid;

import java.net.SocketException;

/**
 * id生成器，每个业务应该保证snowServiceId不一样，这样生成到id才能保证唯一
 *
 * @author wushuaiping
 * @version $Id: IdUtil.java, v 0.1 2020年01月09日 20:17 wushuaiping Exp $
 */
public enum IdUtil {

    /**
     * 用户业务主键生成
     */
    User("001", 1, "用户ID生成"),

    ;

    /**
     * Code
     */
    private String code;

    private long snowServiceId;

    /**
     * 描述
     */
    private String desc;

    IdUtil(String code, long snowServiceId, String desc) {
        this.code = code;
        this.desc = desc;
        this.snowServiceId = snowServiceId;
    }

    /**
     *
     */
    private SnowflakeIdGenerator snowflakeIdGenerator;

    {
        try {
            snowflakeIdGenerator = new SnowflakeIdGenerator(snowServiceId);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method for property <tt>snowflakeIdGenerator</tt>.
     *
     * @return property value of snowflakeIdGenerator
     */
    private SnowflakeIdGenerator getSnowflakeIdGenerator() {
        return snowflakeIdGenerator;
    }

    /**
     * @return
     */
    public String genID() {
        long nextId = snowflakeIdGenerator.nextId();
        return nextId + code;
    }
}