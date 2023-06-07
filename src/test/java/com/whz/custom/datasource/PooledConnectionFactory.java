package com.whz.custom.datasource;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;

import java.sql.*;

/**
 * 用于创建池对象的工厂：用于创建Connection对象的工厂
 *
 * @Author 盖伦
 * @Date 2023/2/25
 */
public class PooledConnectionFactory implements PooledObjectFactory<Connection> {

    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/test";
    String username = "root";
    String password = "123456";

    private volatile String validationQuery = "select 1";

    /**
     * 创建一个对象实例，并将其包装在{@link PooledObject}中，以便由池进行管理
     *
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<Connection> makeObject() throws Exception {

        // 根据JDBC驱动名加载对应的数据库驱动
        Class.forName(driver);
        // 加载完驱动后，就可以使用驱动管理器创建Connection对象了
        Connection connection = DriverManager.getConnection(url, username, password);

        return new PooledConnection(connection);
    }

    /**
     * 销毁该对象实例
     *
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<Connection> pooledObject) throws Exception {
        pooledObject.getObject().close();
    }

    /**
     * 当对象被借出去前都会调用该方法，给对象进行初始化，初始化完成后，才允许借出
     *
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<Connection> pooledObject) throws Exception {
        Connection connection = pooledObject.getObject();
        if (connection.isClosed()) {
            throw new SQLException("validateConnection: connection closed");
        }

        // 做一些连接的初始化动作
        // connection.setAutoCommit(defaultAutoCommit);
        // connection.setReadOnly(defaultReadOnly);
        // connection.setTransactionIsolation(defaultTransactionIsolation);
        // connection.setCatalog(defaultCatalog);
    }

    /**
     * 检查该对象实例是否是初始化的状态，当对象被借出去前，或者归还到池子前都会调用该方法，如果对象无效，则无法借出或者归还会池子，此时返回false
     *
     * @param pooledObject
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<Connection> pooledObject) {
        try {
            Connection connection = pooledObject.getObject();
            PreparedStatement statement = connection.prepareStatement(validationQuery);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return false;
            }
        } catch (final SQLException sqle) {
            return false;
        }

        return true;
    }

    /**
     * 反初始化，每次回收的时候都会执行这个方法
     *
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<Connection> pooledObject) throws Exception {
        Connection connection = pooledObject.getObject();
        if (connection.isClosed()) {
            throw new SQLException("validateConnection: connection closed");
        }
        connection.clearWarnings();
    }


    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
}
