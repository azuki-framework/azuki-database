/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.azkfw.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.BeanPropertySetterRule;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreateRule;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.digester.SetPropertiesRule;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.azkfw.log.LoggingObject;
import org.azkfw.util.StringUtility;
import org.xml.sax.SAXException;

/**
 * このクラスは、データベース管理を行うマネージャークラスです。
 * 
 * @author Kawakicchi
 */
public class DatabaseManager extends LoggingObject {

	/** Instance */
	private static final DatabaseManager INSTANCE = new DatabaseManager();

	/** データソース */
	private Map<String, DataSource> datasources;

	/**
	 * コンストラクタ
	 */
	private DatabaseManager() {
		datasources = new HashMap<String, DataSource>();
	}

	/**
	 * インスタンスを取得する。
	 * 
	 * @return インスタンス
	 */
	public static DatabaseManager getInstance() {
		return INSTANCE;
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param file 設定ファイル
	 */
	public void load(final File file) throws SAXException, IOException {
		load(new FileInputStream(file));
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param file 設定ファイル
	 */
	@SuppressWarnings("unchecked")
	public void load(final InputStream stream) throws SAXException, IOException {
		List<DatasourceEntity> dsList = null;
		try {
			Digester digester = getDigester();
			dsList = (List<DatabaseManager.DatasourceEntity>) digester.parse(stream);
		} catch (Exception ex) {
			fatal(ex);
			throw ex;
		}
		for (DatasourceEntity ds : dsList) {
			if (StringUtility.isEmpty(ds.getName())) {
				addDatasource(ds.getDriver(), ds.getUrl(), ds.getUser(), ds.getPassword());
			} else {
				addDatasource(ds.getName(), ds.getDriver(), ds.getUrl(), ds.getUser(), ds.getPassword());
			}
		}
	}

	/**
	 * 初期化処理を行う
	 */
	public void initialize() {

	}

	/**
	 * 解放処理を行う
	 */
	public void destroy() {

	}

	/**
	 * データーソースを追加する。
	 * 
	 * @param driver ドライバ
	 * @param url 接続文字列
	 * @param user ユーザ名
	 * @param password パスワード
	 */
	public void addDatasource(final String driver, final String url, final String user, final String password) {
		addDatasource(null, driver, url, user, password);
	}

	/**
	 * データーソースを追加する。
	 * 
	 * @param name データソース名
	 * @param driver ドライバ
	 * @param url 接続文字列
	 * @param user ユーザ名
	 * @param password パスワード
	 */
	public void addDatasource(final String name, final String driver, final String url, final String user, final String password) {
		PoolProperties p = new PoolProperties();
		p.setUrl(url);
		p.setDriverClassName(driver);
		p.setUsername(user);
		p.setPassword(password);
		setDefaultOption(p);

		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);
		datasources.put(name, datasource);
	}

	/**
	 * コネクションを取得する。
	 * 
	 * @return コネクション
	 * @throws SQLException SQLに起因する問題が発生した場合
	 */
	public Connection getConnection() throws SQLException {
		return getConnection(null);
	}

	/**
	 * コネクションを取得する。
	 * 
	 * @param name データソース名
	 * @return コネクション
	 * @throws SQLException SQLに起因する問題が発生した場合
	 */
	public Connection getConnection(final String name) throws SQLException {
		DataSource ds = datasources.get(name);
		return ds.getConnection();
	}

	private void setDefaultOption(final PoolProperties p) {
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
	}

	private Digester getDigester() {
		Digester digester = new Digester();
		digester.addRule("azuki-database/datasource-list", new ObjectCreateRule(ArrayList.class));
		digester.addRule("azuki-database/datasource-list/datasource", new ObjectCreateRule(DatasourceEntity.class));
		digester.addRule("azuki-database/datasource-list/datasource", new SetPropertiesRule("name", "name"));
		digester.addRule("azuki-database/datasource-list/datasource/driver", new BeanPropertySetterRule("driver"));
		digester.addRule("azuki-database/datasource-list/datasource/url", new BeanPropertySetterRule("url"));
		digester.addRule("azuki-database/datasource-list/datasource/user", new BeanPropertySetterRule("user"));
		digester.addRule("azuki-database/datasource-list/datasource/password", new BeanPropertySetterRule("password"));
		digester.addRule("azuki-database/datasource-list/datasource", new SetNextRule("add"));
		return digester;
	}

	/**
	 * このクラスは、データソースエンティティです。
	 * 
	 * @author Kawakicchi
	 */
	public static class DatasourceEntity {

		private String name;
		private String driver;
		private String url;
		private String user;
		private String password;

		public void setName(final String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setDriver(final String driver) {
			this.driver = driver;
		}

		public String getDriver() {
			return driver;
		}

		public void setUrl(final String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}

		public void setUser(final String user) {
			this.user = user;
		}

		public String getUser() {
			return user;
		}

		public void setPassword(final String password) {
			this.password = password;
		}

		public String getPassword() {
			return password;
		}
	}
}
