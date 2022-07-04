package org.isaacmcfadyen;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class D1Statement extends D1Queryable implements Statement {
    private boolean closed = false;
    private D1ResultSet currentResultSet;
    private final D1Connection parentConnection;

    D1Statement(String ApiKey, String AccountId, String DatabaseUuid, D1Connection connection) {
        super(ApiKey, AccountId, DatabaseUuid);
        this.parentConnection = connection;
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        JSONObject json = queryDatabase(sql);
        return generateResultSet(json, sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        queryDatabase(sql);
        return 0;
    }

    @Override
    public void close() throws SQLException {
        synchronized (this) {
            closed = true;
            if(currentResultSet != null) {
                currentResultSet.close();
            }
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 10;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {
        this.close();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        JSONObject json = queryDatabase(sql);
        currentResultSet = generateResultSet(json, sql);
        return json.getJSONArray("results").length() > 0;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return currentResultSet;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        // TODO: This is basically an indicator. When a SELECT query updates some rows this will return
        // the number of rows updated, so the DB engine can requery to see the updates.
        // Because D1 doesn't return update count yet we just return -1 to everything.
        return -1;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {}
    @Override
    public int getFetchDirection() throws SQLException {
        return ResultSet.FETCH_FORWARD;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return ResultSet.CONCUR_READ_ONLY;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return ResultSet.TYPE_FORWARD_ONLY;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw new SQLException("Not implemented: addBatch(String sql)");
    }

    @Override
    public void clearBatch() throws SQLException {
        throw new SQLException("Not implemented: clearBatch()");
    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw new SQLException("Not implemented: executeBatch()");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return parentConnection;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new SQLException("Not implemented: getGeneratedKeys()");
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new SQLException("Not implemented: executeUpdate(String sql, int autoGeneratedKeys)");
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLException("Not implemented: executeUpdate(String sql, int[] columnIndexes)");
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new SQLException("Not implemented: executeUpdate(String sql, String[] columnNames)");
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw new SQLException("Not implemented: execute(String sql, int autoGeneratedKeys)");
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLException("Not implemented: execute(String sql, int[] columnIndexes)");
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new SQLException("Not implemented: execute(String sql, String[] columnNames)");
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new SQLException("Not implemented: getResultSetHoldability()");
    }

    @Override
    public boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new SQLException("Not implemented: setPoolable(boolean poolable)");
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new SQLException("Not implemented: isPoolable()");
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new SQLException("Not implemented: closeOnCompletion()");
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new SQLException("Not implemented: isCloseOnCompletion()");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}