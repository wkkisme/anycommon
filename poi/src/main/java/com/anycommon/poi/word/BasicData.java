package com.anycommon.poi.word;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.anycommon.poi.annotation.NoFormat;


public class BasicData {

    @Excel(name = "id")
    private String id;
    @Excel(name = "dataTypeId")
    private String dataTypeId;
    @Excel(name = "name")
    private String name;
    @Excel(name = "code")
    private String code;

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



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BasicData{" +
                "id='" + id + '\'' +
                ", dataTypeId='" + dataTypeId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
