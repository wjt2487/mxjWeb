package cn.mxj.mxjweb.utils;

import java.io.Reader;

import cn.mxj.io.AppLogger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlConfig {
	private static String configFile = "SqlMapConfig.xml";

	private static SqlMapClient sqlMap;

	/**
	 * 获取 SqlMapClient 实例
	 * 
	 * @return
	 */
	
	public static SqlMapClient getSqlMapInstance() {
		if (sqlMap == null) {
			try {
				Reader reader = Resources.getResourceAsReader(configFile);
				sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
			} catch (Exception ex) {
				AppLogger.getInstance().exception(ex);
			}
		}
		return sqlMap;
	}
}
