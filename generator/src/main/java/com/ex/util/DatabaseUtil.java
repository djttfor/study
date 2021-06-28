package com.ex.util;

import com.ex.database.entity.Column;
import com.ex.database.entity.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseUtil {
    private static final Logger log = LoggerFactory.getLogger(DatabaseUtil.class);

    /**
     * 获取所有表配置
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<Table> getDatabaseConfig(String driverClassName, String url, String username, String password) throws SQLException, ClassNotFoundException {
        return getDatabaseConfig(driverClassName,url,username,password,null,null);
    }

    /**
     * 根据需要生成
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @param selectTables
     * @param excludeTables
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<Table> getDatabaseConfig(String driverClassName, String url, String username, String password,List<String> selectTables,List<String> excludeTables) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            DatabaseMetaData m = connection.getMetaData();
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            ResultSet metaTables = m.getTables(catalog, schema, "%", new String[]{"TABLE"});
            List<Table> tables = new ArrayList<>();
            while(metaTables.next()){
                String tableName = metaTables.getString("TABLE_NAME");
                if(selectTables!=null){//判断该表是否需要生成
                    if (!selectTables.contains(tableName)) {
                        continue;
                    }
                }
                if (excludeTables!=null){//判断该表是否不需要生成，与上面的只有一个会执行
                    if(excludeTables.contains(tableName)){
                        continue;
                    }
                }
                Table table = new Table();
                table.setTableName(tableName);
                table.setTableComment(metaTables.getString("REMARKS"));
                List<Column> columns = new ArrayList<>();
                ResultSet mIndexInfo = m.getIndexInfo(catalog, schema, tableName, false, false);
                String primaryColumn = null;
                while(mIndexInfo.next()){
                    if ("PRIMARY".equals(mIndexInfo.getString("INDEX_NAME"))) {
                        primaryColumn = mIndexInfo.getString("COLUMN_NAME");
                    }
                }
                ResultSet mColumns = m.getColumns(catalog, schema, tableName, "%");
                while (mColumns.next()){
                    Column column = new Column();
                    column.setName(mColumns.getString("COLUMN_NAME"));
                    column.setJdbcType(mColumns.getString("TYPE_NAME"));
                    int javaType = Integer.parseInt(mColumns.getString("DATA_TYPE"));
                    column.setJavaType(Types.getJavaTypeName(javaType));
                    column.setLength(mColumns.getString("COLUMN_SIZE"));
                    column.setDecimalDigits(mColumns.getString("DECIMAL_DIGITS"));
                    column.setNotNull(!mColumns.getBoolean("NULLABLE"));
                    column.setPrimary(column.getName().equals(primaryColumn));
                    column.setAutoIncrement("YES".equals(mColumns.getString("IS_AUTOINCREMENT")));
                    column.setComment(mColumns.getString("REMARKS"));
                    columns.add(column);
                }
                table.setColumnList(columns);
                tables.add(table);
            }
            return tables;
        } catch (SQLException throwables) {
            log.info("获取元数据失败:------------------------------------------------------------------------------------");
            throw new RuntimeException(throwables);
        }finally {
            Objects.requireNonNull(connection);
            connection.close();
        }
    }

}
