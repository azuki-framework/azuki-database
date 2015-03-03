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
package org.azkfw.database.definition.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.azkfw.database.definition.DatabaseDefinition;
import org.azkfw.database.definition.model.DatabaseModel;
import org.azkfw.database.definition.model.SchemaModel;
import org.azkfw.database.definition.model.TableModel;
import org.azkfw.lang.LoggingObject;

/**
 * このクラスは、データベース定義解析を行う為の基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/06
 * @author kawakicchi
 */
public abstract class AbstractDatabaseDefinitionParser extends LoggingObject implements DatabaseDefinitionParser {

	/** データベース定義情報 */
	private DatabaseDefinition definition;

	private DatabaseDefinitionParserOption option;

	private DatabaseDefinitionParserEvent event;
	private List<DatabaseDefinitionParserListener> listeners;

	/**
	 * コンストラクタ
	 */
	public AbstractDatabaseDefinitionParser() {
		super(DatabaseDefinitionParser.class);
		option = null;
		event = new DatabaseDefinitionParserEvent(this);
		listeners = new ArrayList<DatabaseDefinitionParserListener>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param clazz クラス
	 */
	public AbstractDatabaseDefinitionParser(final Class<?> clazz) {
		super(clazz);
		option = null;
		event = new DatabaseDefinitionParserEvent(this);
		listeners = new ArrayList<DatabaseDefinitionParserListener>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param name 名前
	 */
	public AbstractDatabaseDefinitionParser(final String name) {
		super(name);
		option = null;
		event = new DatabaseDefinitionParserEvent(this);
		listeners = new ArrayList<DatabaseDefinitionParserListener>();
	}

	@Override
	public final void setOption(final DatabaseDefinitionParserOption option) {
		this.option = option;
	}

	@Override
	public final void addListener(final DatabaseDefinitionParserListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	@Override
	public final void removeListener(final DatabaseDefinitionParserListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	@Override
	public DatabaseModel parse(final String driver, final String url, final String user, final String password) {
		DatabaseModel result = null;

		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);

			if (null == option) {
				option = new DatabaseDefinitionParserOption();
			}

			definition = getDefinition(connection);

			DatabaseModel database = new DatabaseModel();
			parse(database, connection);
			result = database;

		} catch (ClassNotFoundException ex) {
			fatal(ex);
		} catch (SQLException ex) {
			fatal(ex);
		} finally {
			release(connection);
		}
		return result;
	}

	@Override
	public final DatabaseModel parse(final Connection connection) throws SQLException {
		DatabaseModel database = new DatabaseModel();

		if (null == option) {
			option = new DatabaseDefinitionParserOption();
		}

		definition = getDefinition(connection);

		parse(database, connection);

		return database;
	}

	/**
	 * コネクションを閉じる。
	 * 
	 * @param connection コネクション
	 */
	protected final void release(final Connection connection) {
		if (null != connection) {
			try {
				if (!connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException ex) {
				warn(ex);
			}
		}
	}

	/**
	 * データベース定義を取得する。
	 * 
	 * @param connection コネクション情報
	 * @return データベース定義
	 */
	protected abstract DatabaseDefinition getDefinition(final Connection connection);

	private void parse(final DatabaseModel database, final Connection connection) throws SQLException {
		synchronized (listeners) {
			for (DatabaseDefinitionParserListener l : listeners) {
				l.databaseDefinitionParserStarted(event);
			}
		}

		parseDatabase(database, connection);

		synchronized (listeners) {
			for (DatabaseDefinitionParserListener l : listeners) {
				l.databaseDefinitionParserFinished(event);
			}
		}
	}

	private void parseDatabase(final DatabaseModel database, final Connection connection) throws SQLException {
		List<SchemaModel> schemas = definition.getSchemaList();
		for (SchemaModel schema : schemas) {
			if (option.isEnableSchema(schema)) {
				parseDatabase(database, schema, connection);
			} else {
				debug(String.format("Exclude schema.[%s]", schema.getName()));
			}
		}
	}

	private void parseDatabase(final DatabaseModel database, final SchemaModel schema, final Connection connection) throws SQLException {
		List<TableModel> tables = definition.getTableList(schema);
		for (TableModel table : tables) {
			if (option.isEnableTable(table)) {
				database.addTable(table);
			} else {
				debug(String.format("Exclude table.[%s.%s]", table.getSchema().getName(), table.getName()));
			}
		}
	}
}
