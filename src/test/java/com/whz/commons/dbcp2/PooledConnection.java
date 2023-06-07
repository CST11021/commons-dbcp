package com.whz.commons.dbcp2;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;

/**
 * @Author 盖伦
 * @Date 2023/2/25
 */
public class PooledConnection extends DefaultPooledObject<Connection> implements PooledObject<Connection> {

    public PooledConnection(Connection connection) {
        super(connection);
    }

}
