package com.karzhou.app.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleConnectionPool implements ConnectionPool {

    private final BlockingQueue<Connection> pool;
    private final String url;
    private final String user;
    private final String password;

    public SimpleConnectionPool(int size, String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        this.pool = new LinkedBlockingQueue<>(size);

        for (int i = 0; i < size; i++) {
            Connection realConn = DriverManager.getConnection(url, user, password);
            Connection proxyConn = (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class[]{Connection.class},
                    new ConnectionInvocationHandler(realConn, this)
            );
            pool.offer(proxyConn);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for a connection", e);
        }
    }

    @Override
    public void releaseConnection(Connection connection) {
        pool.offer(connection);
    }

    private static class ConnectionInvocationHandler implements InvocationHandler {
        private final Connection realConnection;
        private final ConnectionPool pool;

        public ConnectionInvocationHandler(Connection realConnection, ConnectionPool pool) {
            this.realConnection = realConnection;
            this.pool = pool;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("close".equals(method.getName())) {
                pool.releaseConnection((Connection) proxy);
                return null;
            } else {
                return method.invoke(realConnection, args);
            }
        }
    }
}
