package com.post.ibaties.common.core;

public class Column {
    private String name;
    private String type;

    public Column() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Column type(String type) {
        this.type = type;
        return this;
    }

    public static Column build(String name) {
        Column column = new Column();
        column.setName(name);
        return column;
    }
}