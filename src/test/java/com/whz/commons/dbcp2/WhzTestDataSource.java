package com.whz.commons.dbcp2;

import javax.sql.DataSource;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @Author 盖伦
 * @Date 2023/2/25
 */
public class WhzTestDataSource implements DataSource {

    private volatile PrintWriter logWriter = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
    private volatile boolean initPool = false;

    private String username;
    private String password;
    private String url;
    private String driver;
    private String validationQuery = "select 1";

    private ConnectionPool connectionPool;

    @Override
    public Connection getConnection() throws SQLException {

        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setUrl(url);
        factory.setDriver(driver);
        factory.setValidationQuery(validationQuery);

        if (!initPool) {
            connectionPool = new ConnectionPool(factory);
            initPool = true;
        }

        Connection connection = null;
        try {
            connection = connectionPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("Not supported by BasicDataSource");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (isWrapperFor(iface)) {
            return iface.cast(this);
        }
        throw new SQLException(this + " is not a wrapper for " + iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface != null && iface.isInstance(this);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported by BasicDataSource");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not supported by BasicDataSource");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
