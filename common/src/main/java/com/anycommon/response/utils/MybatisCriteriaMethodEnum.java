package com.anycommon.response.utils;


/**
 * bqhealth-cloud
 *
 * @author wangkai
 * @date 2020/3/3
 */

public enum MybatisCriteriaMethodEnum {
    /**
     * and  equal
     */
    AND_EQUAL_TO("and","EqualTo"),

    /**
     * and like
     */
    AND_LIKE("and","Like"),

    /**
     *  criteria.andGmtModifiedGreaterThanOrEqualTo()
     */
    AND_GREATER_THAN_OR_EQUAL_TO("and","GreaterThanOrEqualTo")

    ;

    private String preMethodName;

    private String sufMethodName;

    MybatisCriteriaMethodEnum(String preMethodName, String sufMethodName) {
        this.preMethodName = preMethodName;
        this.sufMethodName = sufMethodName;
    }

    public String getPreMethodName() {
        return preMethodName;
    }

    public String getSufMethodName() {
        return sufMethodName;
    }
}
