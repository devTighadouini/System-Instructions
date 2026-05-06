package com.system.registers;

public class Register {

    private String labelName;
    private Integer value;

    public Register(String labelName, Integer value) {
        setLabelName(labelName);
        setValue(value);
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
