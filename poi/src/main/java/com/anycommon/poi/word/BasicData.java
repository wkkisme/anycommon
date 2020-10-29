package com.anycommon.poi.word;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.anycommon.poi.annotation.NoFormat;

/**
 * @ClassName Question
 * @Author wangkai
 * @Date 2020/7/26 13:51
 * @Description Question
 * @Version 1.0
 */
public class BasicData {

    @Excel(name = "id")
    private String id;
    @Excel(name = "dataTypeId")
    private String dataTypeId;
    @Excel(name = "name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BasicData{" +
                "id='" + id + '\'' +
                ", dataTypeId='" + dataTypeId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
