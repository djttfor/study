package com.ex.database.entity;

public class Index {
    private String tableName;

    private String indexName;

    private boolean unique;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        return "Index{" +
                "tableName='" + tableName + '\'' +
                ", indexName='" + indexName + '\'' +
                ", unique=" + unique +
                '}';
    }
}
