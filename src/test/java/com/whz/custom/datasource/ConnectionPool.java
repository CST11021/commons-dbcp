package com.whz.custom.datasource;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.sql.Connection;

/**
 * 通过继承GenericObjectPool类实现连接池
 *
 * @Author 盖伦
 * @Date 2023/2/25
 */
public class ConnectionPool extends GenericObjectPool<Connection> implements ObjectPool<Connection> {

    public ConnectionPool(PooledObjectFactory<Connection> factory) {
        super(factory);
    }

    public ConnectionPool(PooledObjectFactory<Connection> factory, GenericObjectPoolConfig<Connection> config) {
        super(factory, config);
    }

    public ConnectionPool(PooledObjectFactory<Connection> factory, GenericObjectPoolConfig<Connection> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

}
