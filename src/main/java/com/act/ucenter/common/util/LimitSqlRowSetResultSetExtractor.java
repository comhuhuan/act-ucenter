package com.act.ucenter.common.util;

import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LimitSqlRowSetResultSetExtractor extends SqlRowSetResultSetExtractor{
	private int maxRows;
	public LimitSqlRowSetResultSetExtractor(int maxRows){
		this.maxRows = maxRows;
	}
	
	/**
	 * Create a SqlRowSet that wraps the given ResultSet,
	 * representing its data in a disconnected fashion.
	 * <p>This implementation creates a Spring ResultSetWrappingSqlRowSet
	 * instance that wraps a standard JDBC CachedRowSet instance.
	 * Can be overridden to use a different implementation.
	 * @param rs the original ResultSet (connected)
	 * @return the disconnected SqlRowSet
	 * @throws SQLException if thrown by JDBC methods
	 * @see #newCachedRowSet
	 * @see ResultSetWrappingSqlRowSet
	 */
	protected SqlRowSet createSqlRowSet(ResultSet rs) throws SQLException {
		CachedRowSet rowSet = newCachedRowSet();
		if (maxRows>0)
			rowSet.setMaxRows(maxRows);
		rowSet.populate(rs);
		return new ResultSetWrappingSqlRowSet(rowSet);
	}

}
