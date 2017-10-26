package com.act.ucenter.common.util;

import com.act.framework.entity.BaseEntity;
import com.act.mapper.code.IdentityDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;


public class DbUtil {
	/**
	 * 分页SQL
	 */
	public static final String MYSQL_SQL = "select * from ( {0}) sel_tab00 limit {1},{2}";         //mysql
	public static final String POSTGRE_SQL = "select * from ( {0}) sel_tab00 limit {2} offset {1}";//postgresql
	public static final String ORACLE_SQL = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}"; //oracle
	public static final String SQLSERVER_SQL = "select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}"; //sqlserver
	public static final String DB2_SQL="select * from (select a.*,rownumber() over() as rowid from ( {0}) a) tmp where tmp.rowid >={2} and tmp.rowid <= {1}";
	private static Logger logger = LoggerFactory.getLogger(DbUtil.class);
	private static DatabaseType dbType = null;
	private static Map<Class<?>,Class<?>> primitiveClass = new HashMap<Class<?>,Class<?>>();
	
	static {
		primitiveClass.put(String.class, String.class);
		primitiveClass.put(int.class, int.class);
		primitiveClass.put(Integer.class, Integer.class);
		primitiveClass.put(Byte.class, Byte.class);
		primitiveClass.put(byte.class, byte.class);
		primitiveClass.put(Long.class, Long.class);
		primitiveClass.put(long.class, long.class);
		primitiveClass.put(Double.class, Double.class);
		primitiveClass.put(double.class, double.class);
		primitiveClass.put(Float.class, Float.class);
		primitiveClass.put(float.class, float.class);
		primitiveClass.put(Character.class, Character.class);
		primitiveClass.put(char.class, char.class);
		primitiveClass.put(Short.class, Short.class);
		primitiveClass.put(short.class, short.class);
		primitiveClass.put(BigDecimal.class, BigDecimal.class);
		primitiveClass.put(BigInteger.class, BigInteger.class);
		primitiveClass.put(Boolean.class, Boolean.class);
		primitiveClass.put(boolean.class, boolean.class);
		primitiveClass.put(java.util.Date.class, java.util.Date.class);
		primitiveClass.put(java.sql.Date.class, java.sql.Date.class);
		primitiveClass.put(Timestamp.class, Timestamp.class);
		primitiveClass.put(Time.class, Time.class);
	}

	public static DatabaseType getDbType() {
		if (dbType==null){
			String jdbcUrl = ConfigUtil.getString("jdbc.url");
			if (jdbcUrl==null||jdbcUrl.length()==0)
				throw new RuntimeException("No jdbc.url defined in config.properties!");
			if (jdbcUrl.startsWith("jdbc:mysql:"))
				return DatabaseType.MYSQL;
			else if (jdbcUrl.startsWith("jdbc:oracle:"))
				return DatabaseType.ORACLE;
			else if (jdbcUrl.startsWith("jdbc:sqlserver:")||jdbcUrl.startsWith("jdbc:microsoft:sqlserver:"))
				return DatabaseType.SQLSERVER;
			else
				throw new RuntimeException("Unsupport database!");
		}
		return dbType;
	}

	public static int update(String sql,Object...params){
		logger.warn(sql);
		return SpringUtil.getJdbcTemplate().update(sql, params);
	}
	
	/**
	 * Map中的字段名均为小写
	 * @param rs
	 * @return
	 */
	public static List<Map<String,Object>> rowSetToList(SqlRowSet rs){
		SqlRowSetMetaData meta = rs.getMetaData();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		while(rs.next()){
			HashMap<String,Object> row = new HashMap<String,Object>();
			for (int i=0;i<meta.getColumnCount();i++){
				String columnName = meta.getColumnLabel(i+1);
				row.put(columnName.toLowerCase(),rs.getObject(i+1));
			}
			list.add(row);
		}
		return list;
	}
	
	/**
	 * Map中的字段名均为小写
	 * @param rs
	 * @return
	 */
	public static List<Map<String,Object>> rowSetToLinkedHashMapList(SqlRowSet rs){
		SqlRowSetMetaData meta = rs.getMetaData();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		while(rs.next()){
			LinkedHashMap<String,Object> row = new LinkedHashMap<String,Object>();
			for (int i=0;i<meta.getColumnCount();i++){
				String columnName = meta.getColumnLabel(i+1);
				row.put(columnName,rs.getObject(i+1));
			}
			list.add(row);
		}
		return list;
	}

	/**
	 * 查询单个Map对象
	 * @param sql
	 * @param params
	 * @return
	 */
	public static  Map<String,Object> queryForMap(String sql,Object... params){
		logger.warn(sql);
		List<Map<String,Object>> results = queryForMapList(sql, params);
		if (results.size()>1){
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		else if (results.size()==1)
			return results.get(0);
		else
			return null;
	}
	
	/**
	 * 查询单个Map对象
	 * @param sql
	 * @param params
	 * @return
	 */
	public static  Map<String,Object> queryForLinekHashMap(String sql,Object... params){
		logger.warn(sql);
		List<Map<String,Object>> results = queryForLinkedHashMapList(sql, params);
		if (results.size()>1){
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		else if (results.size()==1)
			return results.get(0);
		else
			return null;
	}
	
	@Deprecated
	/**
	 * please use queryForMapList
	 * @param executeSql
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> queryForList(String executeSql,Object...params){
		return queryForMapList(executeSql, params);
	}
	
	/**
	 * 按Map对象列表格式查询
	 * @param executeSql
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> queryForMapList(String executeSql,Object...params){
		return rowSetToList(query(executeSql,params));
	}
	
	/**
	 * 按Map对象列表格式查询
	 * @param executeSql
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> queryForLinkedHashMapList(String executeSql,Object...params){
		return rowSetToLinkedHashMapList(query(executeSql,params));
	}

	@Deprecated
	/**
	 * please use queryPageForMapList
	 * @param executeSql
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> queryPageForList(String executeSql,long start,int limit,Object...params){
		return queryPageForMapList(executeSql, start, limit, params);
	}

	/**
	 * 按Map对象列表格式分页查询
	 * @param executeSql
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> queryPageForMapList(String executeSql,long start,int limit,Object...params){
		return rowSetToList(queryPage(executeSql,start,limit,params));
	}
	
	/**
	 *  获取查询结果集
	 * @return  结果集
	 */
	public static SqlRowSet query(String executeSql,Object...params){
		logger.warn(executeSql);
		return SpringUtil.getJdbcTemplate().queryForRowSet(executeSql,params);
	}

	/**
	 *  获取限定条数的查询结果集
	 * @return  结果集
	 */
	public static SqlRowSet queryLimit(String executeSql,int limit,Object...params){
		logger.warn(executeSql);
		return SpringUtil.getJdbcTemplate().query(executeSql,params,new LimitSqlRowSetResultSetExtractor(limit));
	}
	
	public static <T> T query(String executeSql,Object[] params,ResultSetExtractor<T> rse){
		logger.warn(executeSql);
		return SpringUtil.getJdbcTemplate().query(executeSql, params,rse);
	}
	
	/**
	 * 按照数据库类型，封装SQL
	 * @param dbType 数据库类型
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 */
	public static String createPageSql(DatabaseType dbType, String sql, long start, int limit){
		long beginNum = start;
		String[] sqlParam = new String[3];
		sqlParam[0] = sql;
		sqlParam[1] = beginNum+"";
		sqlParam[2] = limit+"";
		if(dbType==null){
			throw new RuntimeException("缺少数据库类型！");
		}

		if(dbType== DatabaseType.MYSQL){
			sql = MessageFormat.format(MYSQL_SQL, (Object[])sqlParam);
		}else if(dbType== DatabaseType.POSTGRE){
			sql = MessageFormat.format(POSTGRE_SQL, (Object[])sqlParam);
		}else {
			long beginIndex = start;
			long endIndex = beginIndex+limit;
			sqlParam[2] = Long.toString(beginIndex);
			sqlParam[1] = Long.toString(endIndex);
			if(dbType== DatabaseType.ORACLE) {
				sql = MessageFormat.format(ORACLE_SQL, (Object[])sqlParam);
			}
			else if(dbType== DatabaseType.DB2) {
				sql = MessageFormat.format(DB2_SQL, (Object[])sqlParam);
			} else if(dbType== DatabaseType.SQLSERVER) {
				sqlParam[0] = sql.substring(getAfterSelectInsertPoint(sql));
				sql = MessageFormat.format(SQLSERVER_SQL, (Object[])sqlParam);
			}
			else
				throw new RuntimeException("Unknown database: " + dbType);
		}
		return sql;
	}
	
	private static int getAfterSelectInsertPoint(String sql) {
	    int selectIndex = sql.toLowerCase().indexOf("select");
	    int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
	    return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
    }
	
	public static Timestamp toTimestamp(String value){
		if (value!=null && value.toString().toUpperCase().startsWith("TO_DATE")){
			String tmps = value.toString().substring(8).trim();
			if (tmps.startsWith("'"))
				tmps = tmps.substring(1);
			if (tmps.indexOf("'")>=0)
				tmps = tmps.substring(0,tmps.indexOf("'"));
			java.util.Date d;
			if (tmps.length()<=10){
				d = DateUtil.toDate(tmps,"yyyy-MM-dd");
			}else{
				d = DateUtil.toDate(tmps,"yyyy-MM-dd HH:mm:ss");
			}
			if (d!=null)
				return DateUtil.toTimestamp(d);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readObject(ResultSet rs, String columnName,Class<T> javaType) throws SQLException{
		Object value;
		if (String.class==javaType){
			value = rs.getString(columnName);
		}
		else if (Byte.class==javaType||byte.class == javaType){
			value = rs.getByte(columnName);
		}
		else if (Short.class==javaType || short.class == javaType){
			value = rs.getShort(columnName);
		}
		else if (Integer.class==javaType || int.class == javaType){
			value = rs.getInt(columnName);
		}
		else if (Long.class==javaType || long.class == javaType){
			value = rs.getLong(columnName);
		}
		else if (Float.class==javaType || float.class == javaType){
			value = rs.getFloat(columnName);
		}
		else if (Double.class==javaType || double.class == javaType){
			value = rs.getDouble(columnName);
		}
		else if (BigDecimal.class==javaType){
			value = rs.getBigDecimal(columnName);
		}
		else if (java.util.Date.class==javaType){
			value = rs.getTimestamp(columnName);
		}
		else if (Timestamp.class==javaType){
			value = rs.getTimestamp(columnName);
		}
		else if (java.sql.Date.class==javaType){
			value = rs.getDate(columnName);
		}
		else if (boolean.class==javaType){
			value = rs.getBoolean(columnName);
		}
		else if (byte[].class==javaType){
			value = rs.getBytes(columnName);
		}else if (Object.class==javaType){
			value = rs.getObject(columnName);
		}
		else {
			throw new RuntimeException("Unsupport type " + javaType);
		}
		if (rs.wasNull()){
			value = null;
		}
		if (javaType.isPrimitive() && value==null){
			throw new RuntimeException("Primitive type with null value");
		}
		return (T)value;
	}

	public static <T> T queryForObject(String sql, Class<T> type, Object... params) throws DataAccessException {
		logger.warn(sql);
		if (primitiveClass.containsKey(type)){
			//simple object
			List<T> results = SpringUtil.getJdbcTemplate().query(sql, params, new RowMapperResultSetExtractor<T>(
					new SingleColumnRowMapper<T>(type), 1));
			if (results.size()>1){
				throw new IncorrectResultSizeDataAccessException(1, results.size());
			}
			else if (results.size()==1)
				return results.get(0);
			else
				return null;
		}
		else {
			List<T> results = SpringUtil.getJdbcTemplate().query(sql, params, new RowMapperResultSetExtractor<T>(
					new ObjectRowMapper<T>(type), 1));
			if (results.size()>1){
				throw new IncorrectResultSizeDataAccessException(1, results.size());
			}
			else if (results.size()==1)
				return results.get(0);
			else
				return null;

		}
	}

	public static <T> List<T> queryForObjectList(String sql,Class<T> type,Object... params){
		logger.warn(sql);
		if (primitiveClass.containsKey(type)){
			return SpringUtil.getJdbcTemplate().query(sql, params, new RowMapperResultSetExtractor<T>(
					new SingleColumnRowMapper<T>(type)));

		}
		else
			return (List<T>) SpringUtil.getJdbcTemplate().query(sql, new ObjectRowMapper<T>(type),params);
	}

	public static <T> List<T> queryPageForObjectList(String sql,Class<T> type,long start,int limit,Object...params){
		if(limit>0){
			DatabaseType dbType = getDbType();
			sql = createPageSql(dbType,sql, start, limit);
		}
		logger.warn(sql);
		return  queryForObjectList(sql, type, params);
	}
	
	public static int queryForInt(String sql,Object... params){
		logger.warn(sql);
		Integer value = queryForObject(sql, Integer.class,params);
		if (value==null)
			return 0;
		else
			return value;
	}
	
	public static long queryForLong(String sql,Object... params){
		logger.warn(sql);
		Long value= queryForObject(sql, Long.class,params);
		if (value==null)
			return 0L;
		else
			return value;
	}
	
	public static BigDecimal queryForBigDecimal(String sql,Object... params){
		logger.warn(sql);
		return queryForObject(sql, BigDecimal.class,params);
	}

	public static String queryForString(String sql,Object... params){
		logger.warn(sql);
		return queryForObject(sql, String.class,params);
	}
	
	@Deprecated
	/**
	 * 使用queryForObject代替
	 * @param sql
	 * @param type
	 * @param params
	 * @return
	 */
	public static  <T extends BaseEntity> T queryForEntity(String sql,Class<T> type,Object... params){
		logger.warn(sql);
		List<T> results = SpringUtil.getJdbcTemplate().query(sql, params, new RowMapperResultSetExtractor<T>(
				new EntityRowMapper<T>(type), 1));
		if (results.size()>1){
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		else if (results.size()==1)
			return results.get(0);
		else
			return null;
	}
	
	@Deprecated
	/**(
	 * 使用queryForObjectList代替
	 * @param sql
	 * @param type
	 * @param params
	 * @return
	 */
	public static <T extends BaseEntity> List<T> queryForEntityList(String sql,Class<T> type,Object... params){
		logger.warn(sql);
		return (List<T>) SpringUtil.getJdbcTemplate().query(sql, new EntityRowMapper<T>(type),params);
	}

	@Deprecated
	/**
	 * 使用queryPageForObjectList代替
	 * @param sql
	 * @param type
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	public static <T extends BaseEntity> List<T> queryPageForEntityList(String sql,Class<T> type,long start,int limit,Object...params){
		if(limit>0){
			DatabaseType dbType = getDbType();
			sql = createPageSql(dbType,sql, start, limit);
		}
		logger.warn(sql);
		return  (List<T>) SpringUtil.getJdbcTemplate().query(sql, new EntityRowMapper<T>(type),params);
	}
	
	/**
	 * 每页index数从0开始
	 */
	public static <T> PageResult<T> queryPageForObjectPageResult(String sql,Class<T> type,int pageIndex,int pageSize,Object...params){
		Assert.isTrue(pageSize>=0 || pageSize == -1);
		Assert.isTrue(pageIndex>=0);
		String countSql = "select count(1) from (" + sql + ") t";
		long totalCount = DbUtil.queryForLong(countSql, params);
		if (totalCount==0){
			return new PageResult<T>(new ArrayList<T>(), pageIndex, pageSize, 0, 0);
		}
		else {
			if(pageSize == -1){
				pageSize = (int) totalCount;
			}
			List<T> resultList = queryPageForObjectList(sql, type, pageIndex,pageSize,params);
			return new PageResult<T>(resultList, pageIndex, pageSize,(int) (totalCount+pageSize-1)/pageSize, totalCount);
		}
	}

	/**
	 *
	 * @param sql
	 * @param pageIndex
	 * 		从1开始
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public static PageResult<Map<String,Object>> queryPageForMapPageResult(String sql,int pageIndex,int pageSize,Object...params){
		Assert.isTrue(pageSize>=0);
		Assert.isTrue(pageIndex>=0);
		String countSql = "select count(1) from (" + sql + ") t";
		long totalCount = DbUtil.queryForLong(countSql, params);
		if (totalCount==0){
			return new PageResult<Map<String,Object>>(new ArrayList<Map<String,Object>>(), pageIndex, pageSize, 0, 0);
		}
		else {
			List<Map<String,Object>> resultList = queryPageForMapList(sql, (pageIndex-1)*pageSize,pageSize,params);
			return new PageResult<Map<String,Object>>(resultList, pageIndex, pageSize,(int) (totalCount+pageSize-1)/pageSize, totalCount);
		}
	}

	/**
	 *  获取查询结果集
	 * @return  结果集
	 */
	public static SqlRowSet queryPage(String executeSql,long start,int limit,Object...params){
		if(limit>0){
			DatabaseType dbType = getDbType();
			executeSql = createPageSql(dbType,executeSql, start, limit);
		}
		logger.warn(executeSql);
		return SpringUtil.getJdbcTemplate().queryForRowSet(executeSql,params);
	}

	//返回连接字符串的SQL
	public static String concat(String str1,String str2){
		DatabaseType dbType = getDbType();
		if (DatabaseType.MYSQL==dbType){
			return "concat(coalesce(" + str1 + ",''),coalesce(" + str2+",''))";
		}
		else if (DatabaseType.SQLSERVER==dbType){
			return str1 + " + " + str2;
		}
		else
			//Oracle,Postgre,DB2
			return str1 + " || " + str2;
	}

	//返回连接一组字符串的SQL
	public static String concat_ws(String[] strs,String sepeartor){
		Assert.notEmpty(strs);
		DatabaseType dbType = getDbType();
		if (DatabaseType.MYSQL==dbType){
			StringBuffer sql = new StringBuffer("CONCAT_WS(");
			sql.append("'").append(sepeartor).append("',");
			boolean first = true;
			for (String str: strs){
				if (!first){
					sql.append(",");
				}
				sql.append("IFNULL(").append(str).append(",").append("'')");
				first = false;
			}
			sql.append(")");
			return sql.toString();
		}
		else if (DatabaseType.SQLSERVER==dbType){
			boolean first=true;
			StringBuffer sql = new StringBuffer("");
			for (String str: strs){
				if (!first){
					sql.append(" + '" + sepeartor+"' + ");
				}
				sql.append(str);
				first = false;
			}
			return sql.toString();
		}
		else {
			//Oracle,Postgre,DB2
			boolean first=true;
			StringBuffer sql = new StringBuffer("");
			for (String str: strs){
				if (!first) {
					sql.append(" || '" + sepeartor+"' || ");
				}
				sql.append(str);
				first = false;
			}
			return sql.toString();
		}
	}
	
	public static String getIsNullSql(String column){
		if (DatabaseType.MYSQL==getDbType()){
			return "(" + column + " IS NULL OR " + column + "='')";
		}
		else {
			return column + " IS NULL";
		}
	}

	public static String getIsNotNullSql(String column){
		if (DatabaseType.MYSQL==getDbType()){
			return "(" + column + "!='')";
		}
		else {
			return column + " IS NOT NULL";
		}
	}

	/**
	 * 是否允许插入自增长列
	 * @return
	 */
	public static boolean isAlllowIdentityInsert() {
		return isSqlServer();
	}
	
	public static boolean isSqlServer() {
		return DatabaseType.SQLSERVER==getDbType();
	}

	public static IdentityDialect getIdentityDialect(){
		switch (getDbType())
		{
		case MYSQL:
			return IdentityDialect.MYSQL;
		case SQLSERVER:
			return IdentityDialect.SQLSERVER;
		default:
			throw new RuntimeException("Unsupport database: " + getDbType());
		}

	}
	
	/**
	 * @Title: getHeaderBySql
	 * @Description: 根据sql语句得到表头数组
	 * @param @param sql
	 * @return String[]   sq语句表头
	 * @author fmj
	 * @create 2017-5-10 下午12:07:16
	 * @update 2017-5-10 下午12:07:16
	 */
	public static String[] getHeaderBySql(String sql) {
		logger.warn(sql);
		RowCountCallbackHandler rcch = new RowCountCallbackHandler();
		SpringUtil.getJdbcTemplate().query(sql, rcch);
		return rcch.getColumnNames();
	}
	
	/**
	 * @Title: pageMapListBySql
	 * @Description: 根据sql返回分页结果集
	 * @create 2017-5-10 下午5:32:52
	 * @update 2017-5-10 下午5:32:52
	 */
	public static PageResult<Map<String, Object>> pageMapListBySql(String sql,int pageIndex,int pageSize) {
		logger.warn(sql);
		String countSql = "select count(*) from (" + sql + ") t";
		long totalCount = DbUtil.queryForLong(countSql);
		if (totalCount==0){
			return new PageResult<Map<String, Object>>(new ArrayList<Map<String, Object>>(), pageIndex, pageSize, 0, 0);
		}else{
			if(pageSize == -1){
				pageSize = (int) totalCount;
			}
		}
		String[] sqlParam = new String[3];
		sqlParam[0] = sql;
		sqlParam[1] = pageIndex+"";
		sqlParam[2] = pageSize+"";
		String querySql = MessageFormat.format(MYSQL_SQL, (Object[])sqlParam);
		List<Map<String, Object>> data = SpringUtil.getJdbcTemplate().queryForList(querySql);
		return new PageResult<Map<String, Object>>(data, pageIndex, pageSize,(int) (totalCount+pageSize-1)/pageSize, totalCount);
	}
	
	public enum DatabaseType {
		MYSQL,
		POSTGRE,
		ORACLE,
		SQLSERVER,
		DB2
	}

}





