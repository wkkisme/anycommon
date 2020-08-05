package com.anycommon.response.constant;

public enum ErrMsgEnum {

    /**
     * 参数为空
     */
    EMPTY_PARAME("10001", "参数为空"),

    SUCCESS("200","操作成功"),

    DYNAMIC_DECRYPT_ERR("10002", "动态解密失败"),

    ERROR_PARAME("ERROR_PARAME", "参数错误"),

    ERROR_SYSTEM("ERROR_SYSTEM", "系统错误"),

    EMPTY_DATA("EMPTY_DATA", "数据为空"),

    ERROR_DATA("ERROR_DATA", "非法数据"),

    CAPTCHA_ERROR("40001", "验证码错误"),

    CAPTCHA_EXPIRE("40002", "验证码过期"),

    USER_OR_PWD_ERROR("40003", "用户名或密码错误"),

    USER_FREEZ("40004", "用户名已冻结,无法登录"),

    USER_LOGOFF("40005", "用户名已注销,无法登录"),

    LOGIN_TIMEOUT("40006", "登录超时,请重新登录"),

    SESSION_ID_EXPIRE("40007", "sessionId,请重新登录"),

    GLOBAL_SESSIOB_ID_EMPTY("40007", "globalSessionId不能为空！"),

    USER_ACCOUNT_NOT_EXIT("40008", "用户账户不存在"),


    /**
     * 忘记密码
     */
    FORGET_PHONE_NOT_REG("41000", "手机号未注册"),

    FORGET_CAPTCHA_ERROR("41001", "验证码错误"),

    FORGET_CAPTCHA_EXPIRE("42002", "验证码过期"),

    FORGET_USER_FREEZ("41003", "用户名已冻结,无法修改密码"),

    FORGET_USER_LOGOFF("41004", "用户名已注销,无法修改密码"),

    FORGET_PHONE_FORMAT_ERR("41005", "手机号格式错误"),


    /**
     * 服务器内部错误
     */
    SERVER_ERROR("50000", "服务器内部错误"),


    /* 注册用户 */
    USERNAME_EXISTED("50001", "用户名已存在"),

    REGISTER_TIMEOUT("50002", "注册超时,请重新注册"),

    PHONE_REGISTERED("50003", "手机号已注册,请直接登录"),

    NOT_CERTIFICATION("50004", "未实名认证,请继续实名认证"),

    CAPTCHA_ERR_OR_EXPIRE("50005", "验证码错误或过期"),


    /*用户名输入为空，请输入用户名*/
    INPUT_USERNAME("INPUT_USERNAME", "请输入用户名"),

    /*密码输入为空，请输入密码*/
    INPUT_PASSWORD("INPUT_PASSWORD", "请输入密码"),

    /*用户名输入错误，用户名不存在*/
    USERNAME_INVALID("USERNAME_INVALID", "用户名不存在"),

    /*密码输入错误*/
    PASSWORD_INVALID("PASSWORD_INVALID", "密码输入错误"),

    /*用户未登录*/
    PLEASE_LOGIN("PLEASE_LOGIN", "用户未登录"),

    /*用户状态更新失败*/
    UPDATE_STATUS_FAILED("UPDATE_STATUS_FAILED", "更新用户状态失败"),

    /*用户信息更新失败*/
    UPDATE_FAILED("UPDATE_FAILED", "更新失败"),

    /*删除用户失败*/
    DELETE_FAILED("DELETE_FAILED", "删除用户失败"),

    /*应用系统中文名称重复*/
    APP_CODE_EXISTED("APP_CODE_EXISTED", "应用系统码已存在"),


    APP_NOT_EXIT("APP_NOT_EXIT", "应用系统不存在"),

    USER_ERROR("USER_ERROR", "用户不匹配"),

    ILLEGAL_USER("ILLEGAL_USER", "非法用户"),

    LOCAL_SESSION_DOES_NOT_EXIST("LOCAL_SESSION_DOES_NOT_EXIST", "局部会话不存在"),

    /*redis中不存在该sessionID*/
    KEY_NOT_EXIST("KEY_NOT_EXIST", "redis中不存在该sessionID/username"),


    EXIST_CHILD_MENU("", "删除失败,存在子菜单或子功能点"),

    LOCK_USER("LOCKED", "提示尝试次数过多请联系管理员。"),

    PERMISSION_NOT_EXIST("PERMISSION_NOT_EXIST","权限不存在"),
    ROLE_NOT_EXIST("ROLE_NOT_EXIST","角色不存在"),
    USER_NOT_EXIST("USER_NOT_EXIST","用户不存在"),
    ;

    private String errorCode;
    private String errorMessage;

    ErrMsgEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
