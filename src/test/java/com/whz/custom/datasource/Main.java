package com.whz.custom.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 盖伦
 * @Date 2023/2/25
 */
public class Main {

    public static void main(String[] args) throws Exception{
        WhzTestDataSource dataSource = new WhzTestDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/group_meal?useUnicode=yes&characterEncoding=UTF-8&useSSL=false");
        dataSource.setDriver("com.mysql.jdbc.Driver");

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USER");
        List list = showResult(resultSet);

        resultSet.close();
        statement.close();
        connection.close();
    }

    public static List showResult(ResultSet rs) throws SQLException {

        // 列数
        int numFields = rs.getMetaData().getColumnCount();

        // 字段名
        String[] fieldNames = new String[numFields];
        for (int i = 0; i < numFields; i++) {
            fieldNames[i] = rs.getMetaData().getColumnLabel(i + 1);
        }


        List rows = new ArrayList();
        // 遍历结果
        int rowCount = 1;
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();

            row.put("序号", rowCount);
            for (int i = 0; i < numFields; i++) {

                Object value = rs.getObject(i + 1);
                if (value != null) {
                    row.put(fieldNames[i], rs.getObject(i + 1));
                }
            }

            rowCount++;
            rows.add(row);
        }

        return rows;
    }

}
