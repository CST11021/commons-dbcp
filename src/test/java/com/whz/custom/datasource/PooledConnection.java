package com.whz.custom.datasource;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;

/**
 * 需要将原始的Connection对象包装为PooledObject后，才能放到对象池中，这样对象池才能管理这些对象
 *
 * @Author 盖伦
 * @Date 2023/2/25
 */
public class PooledConnection extends DefaultPooledObject<Connection> implements PooledObject<Connection> {

    public PooledConnection(Connection connection) {
        super(connection);
    }

}
