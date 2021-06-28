package com.ex.database.entity;

public class Column {
    private String name;

    private String jdbcType;

    private String javaType;

    private String length;

    private boolean notNull;

    private boolean primary;

    private boolean autoIncrement;

    private String comment;

    private String decimalDigits;

    public String getDecimalDigits() {
        return decimalDigits;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", jdbcType='" + jdbcType + '\'' +
                ", javaType='" + javaType + '\'' +
                ", length='" + length + '\'' +
                ", notNull=" + notNull +
                ", primary=" + primary +
                ", autoIncrement=" + autoIncrement +
                ", comment='" + comment + '\'' +
                ", decimalDigits='" + decimalDigits + '\'' +
                '}';
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public void setDecimalDigits(String decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
