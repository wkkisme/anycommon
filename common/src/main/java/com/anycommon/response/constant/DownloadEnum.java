package com.anycommon.response.constant;

/**
 * @author wangkai
 */

public enum DownloadEnum {
    /**
     * 图片
     */
    PNG("image/png;charset=UTF-8"),
    /**
     * jpg
     */
    JPG("image/jpg;charset=UTF-8"),
    /**
     * html
     */
    HTML("text/html;charset=UTF-8"),

    /**
     * markdown
     */
    MD("text/x-markdown;charset=UTF-8"),

    /**
     * 纯文本
     */
    PLAIN("text/plain;charset=UTF-8"),
    /**
     * XML
     */
    XML("text/xml;charset=UTF-8"),
    /**
     * gif
     */
    GIF("image/gif;charset=UTF-8"),

    /**
     * jpeg
     */
    JPEG("image/jpeg;charset=UTF-8"),
    /**
     * pdf
     */
    PDF("application/pdf;charset=UTF-8"),
    /**
     * bmp
     *
     */
    BMP("application/x-bmp;charset=UTF-8")
    ;

    private String contentType;

    DownloadEnum(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 根据文件后缀获取contentType
     * @param suffix
     * @return
     */
    public static String getContentTpye(String suffix){

        DownloadEnum[] values = DownloadEnum.values();
        for (DownloadEnum value : values) {
            if (value.toString().equalsIgnoreCase(suffix)){
                return value.contentType;
            }
        }

        return null;

    }
    public String getContentType() {
        return contentType;
    }
}
