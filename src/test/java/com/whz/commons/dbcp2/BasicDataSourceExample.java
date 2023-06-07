package com.whz.commons.dbcp2;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * dbcp数据源连接池使用示例
 *
 * @author duanxj
 * @version 1.0
 */
public class BasicDataSourceExample {

    /**
     *
     * @param args  args[0]: connectURI
     *              args[1]: sql
     */
    public static void main(String[] args) {
        // 设置数据源基本配置项
        DataSource dataSource = setupDataSource(args[0]);

        // 创建连接
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        try {
            // 创建连接对象
            conn = dataSource.getConnection();
            // 创建Statement 对象，这里我们使用Statement prepareStatement也是一样的
            stmt = conn.createStatement();
            // 创建结果返回集
            rset = stmt.executeQuery(args[1]);
            // 得到查询影响记录数
            int numcols = rset.getMetaData().getColumnCount();
            while (rset.next()) {
                for (int i = 1; i <= numcols; i++) {
                    System.out.print("\t" + rset.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (rset != null) rset.close();
            } catch (Exception e) {}

            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }

    /**
     * 创建数据源，并设置数据源基本配置项
     *
     * @param connectURI
     * @return
     */
    public static DataSource setupDataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        // 设置驱动
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        // 设置用户名
        ds.setUsername("root");
        // 设置密码
        ds.setPassword("root");
        // 设置连接url
        ds.setUrl(connectURI);
        return ds;
    }

    /**
     * 打印创建的数据源的配置项
     *
     * @param ds
     */
    public static void printDataSourceStats(DataSource ds) {
        BasicDataSource bds = (BasicDataSource) ds;
        System.out.println("NumActive: " + bds.getNumActive());
        System.out.println("NumIdle: " + bds.getNumIdle());
    }

    /**
     * 关闭销毁
     *
     * @param ds
     * @throws SQLException
     */
    public static void shutdownDataSource(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        bds.close();
    }
}