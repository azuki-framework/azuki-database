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
package org.azkfw.database.definition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.azkfw.database.definition.model.FieldModel;
import org.azkfw.database.definition.model.FieldTypeModel;
import org.azkfw.database.definition.model.ForeignKeyFeildModel;
import org.azkfw.database.definition.model.ForeignKeyModel;
import org.azkfw.database.definition.model.IndexFieldModel;
import org.azkfw.database.definition.model.IndexModel;
import org.azkfw.database.definition.model.SchemaModel;
import org.azkfw.database.definition.model.TableModel;
import org.azkfw.lang.LoggingObject;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、データベース定義機能を定義する為の基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/03/03
 * @author kawakicchi
 */
public abstract class AbstractDatabaseDefinition extends LoggingObject implements DatabaseDefinition {

	private Connection connection;

	public AbstractDatabaseDefinition(final Class<?> clazz, final Connection connection) {
		super(clazz);

		this.connection = connection;
	}

	@Override
	public List<SchemaModel> getSchemaList() throws SQLException {
		List<SchemaModel> schemas = new ArrayList<SchemaModel>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// テーブル一覧取得
			ps = connection.prepareStatement(getSchemaSQL());
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");

				SchemaModel schema = new SchemaModel();
				schema.setName(name);

				schemas.add(schema);
			}

		} finally {
			release(rs);
			release(ps);
		}

		return schemas;
	}

	@Override
	public List<TableModel> getTableList(final SchemaModel schema) throws SQLException {
		List<TableModel> tables = new ArrayList<TableModel>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// テーブル一覧取得
			ps = connection.prepareStatement(getTableListSQL());
			ps.setString(1, schema.getName());
			rs = ps.executeQuery();
			while (rs.next()) {
				String label = rs.getString("label");
				String name = rs.getString("name");
				String comment = rs.getString("comment");

				TableModel table = new TableModel();
				table.setLabel(label);
				table.setSchema(schema);
				table.setName(name);
				table.setComment(comment);

				parseTable(table, connection);

				tables.add(table);
			}

		} finally {
			release(rs);
			release(ps);
		}

		return tables;
	}

	@Override
	public TableModel getTable(final SchemaModel schema, final String tableName) throws SQLException {
		TableModel result = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// テーブル一覧取得
			ps = connection.prepareStatement(getTableSQL());
			ps.setString(1, schema.getName());
			ps.setString(2, tableName);
			rs = ps.executeQuery();
			if (rs.next()) {
				String label = rs.getString("label");
				String name = rs.getString("name");
				String comment = rs.getString("comment");

				TableModel table = new TableModel();
				table.setLabel(label);
				table.setSchema(schema);
				table.setName(name);
				table.setComment(comment);

				parseTable(table, connection);

				result = table;
			}

		} finally {
			release(rs);
			release(ps);
		}

		return result;
	}

	protected final void release(final ResultSet rs) {
		if (null != rs) {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException ex) {
				warn(ex);
			}
		}
	}

	protected final void release(final PreparedStatement ps) {
		if (null != ps) {
			try {
				if (!ps.isClosed()) {
					ps.close();
				}
			} catch (SQLException ex) {
				warn(ex);
			}
		}
	}

	/**
	 * スキーマ情報を取得するSQLを取得する。
	 * <p>
	 * バインド変数は下記の通りです。
	 * <ul>
	 * </ul>
	 * </p>
	 * <p>
	 * 取得するカラムは下記の通りです。
	 * <ul>
	 * <li>name - スキーマ名</li>
	 * </ul>
	 * </p>
	 * 
	 * @return SQL
	 */
	protected abstract String getSchemaSQL();

	/**
	 * テーブル情報一覧を取得するSQLを取得する。
	 * <p>
	 * バインド変数は下記の通りです。
	 * <ul>
	 * <li>1 - スキーマ名</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 取得するカラムは下記の通りです。
	 * <ul>
	 * <li>label - 論理テーブル名</li>
	 * <li>name - 物理テーブル名</li>
	 * <li>schema - スキーマ</li>
	 * <li>comment - コメント</li>
	 * </ul>
	 * </p>
	 * 
	 * @return SQL
	 */
	protected abstract String getTableListSQL();

	/**
	 * テーブル情報を取得するSQLを取得する。
	 * <p>
	 * バインド変数は下記の通りです。
	 * <ul>
	 * <li>1 - スキーマ名</li>
	 * <li>2 - テーブル名</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 取得するカラムは下記の通りです。
	 * <ul>
	 * <li>label - 論理テーブル名</li>
	 * <li>name - 物理テーブル名</li>
	 * <li>schema - スキーマ</li>
	 * <li>comment - コメント</li>
	 * </ul>
	 * </p>
	 * 
	 * @return SQL
	 */
	protected abstract String getTableSQL();

	/**
	 * フィールド情報を取得するSQLを取得する。
	 * <p>
	 * バインド変数は下記の通りです。
	 * <ul>
	 * <li>1 - スキーマ名</li>
	 * <li>2 - テーブル名</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 取得するカラムは下記の通りです。
	 * <ul>
	 * <li>label - 論理フィールド名</li>
	 * <li>name - 物理フィールド名</li>
	 * <li>type - タイプ</li>
	 * <li>extra - 付加情報</li>
	 * <li>notnull - NULL制約</li>
	 * <li>default - デフォルト値</li>
	 * <li>comment - コメント</li>
	 * </ul>
	 * </p>
	 * 
	 * @return SQL
	 */
	protected abstract String getFieldSQL();

	/**
	 * インデックス情報を取得するSQLを取得する。
	 * <p>
	 * バインド変数は下記の通りです。
	 * <ul>
	 * <li>1 - スキーマ名</li>
	 * <li>2 - テーブル名</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 取得するカラムは下記の通りです。
	 * <ul>
	 * <li>name - フィールド名</li>
	 * <li>field_name - フィールド名</li>
	 * <li>unique_key - ユニークキー</li>
	 * <li>primary_key - プライマリキー</li>
	 * </ul>
	 * </p>
	 * 
	 * @return SQL
	 */
	protected abstract String getIndexSQL();

	/**
	 * 外部キー情報を取得するSQLを取得する。
	 * <p>
	 * バインド変数は下記の通りです。
	 * <ul>
	 * <li>1 - スキーマ名</li>
	 * <li>2 - テーブル名</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 取得するカラムは下記の通りです。
	 * <ul>
	 * <li>name - 外部キー名</li>
	 * <li>field_name - フィールド名</li>
	 * <li>ref_table_name - 参照先テーブル名</li>
	 * <li>ref_field_name - 参照先フィールド名</li>
	 * </ul>
	 * </p>
	 * 
	 * @return SQL
	 */
	protected abstract String getForeignKeySQL();

	private void parseTable(final TableModel table, final Connection connection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// フィールド情報取得
			ps = connection.prepareStatement(getFieldSQL());
			ps.setString(1, table.getSchema().getName());
			ps.setString(2, table.getName());
			rs = ps.executeQuery();
			while (rs.next()) {
				String label = rs.getString("label");
				String name = rs.getString("name");
				String type = rs.getString("type");
				String extra = rs.getString("extra");
				Boolean notnull = rs.getBoolean("notnull");
				Object def = rs.getObject("default");
				String comment = rs.getString("comment");

				FieldTypeModel fieldType = new FieldTypeModel();
				fieldType.setLabel(type);

				FieldModel field = new FieldModel();
				field.setLabel(label);
				field.setName(name);
				field.setType(fieldType);
				field.setExtra(extra);
				field.setNotNull(notnull);
				if (null == def) {
					field.setDefaultFlag(false);
				} else {
					field.setDefaultFlag(true);
					field.setDefaultValue(def);
				}
				field.setComment(comment);

				table.addField(field);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			// インデックス情報取得
			{
				String sql = getIndexSQL();
				if (StringUtility.isNotEmpty(sql)) {
					ps = connection.prepareStatement(sql);
					ps.setString(1, table.getSchema().getName());
					ps.setString(2, table.getName());
					rs = ps.executeQuery();
					while (rs.next()) {
						String name = rs.getString("name");
						String fieldName = rs.getString("field_name");
						Boolean unique = rs.getBoolean("unique_key");
						Boolean primaryKey = rs.getBoolean("primary_key");

						IndexModel index = table.getIndex(name);
						if (null == index) {
							index = new IndexModel();
							index.setName(name);
							index.setPrimaryKey(primaryKey);
							index.setUnique(unique);
							table.addIndex(index);
						}

						IndexFieldModel field = new IndexFieldModel();
						field.setName(fieldName);

						index.addField(field);
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
			}

			// 外部キー
			{
				String sql = getForeignKeySQL();
				if (StringUtility.isNotEmpty(sql)) {
					ps = connection.prepareStatement(sql);
					ps.setString(1, table.getSchema().getName());
					ps.setString(2, table.getName());
					rs = ps.executeQuery();
					while (rs.next()) {
						String name = rs.getString("name");
						String fieldName = rs.getString("field_name");
						String refTableName = rs.getString("ref_table_name");
						String refFieldName = rs.getString("ref_field_name");

						ForeignKeyModel foreignKey = table.getForeignKey(name);
						if (null == foreignKey) {
							foreignKey = new ForeignKeyModel();
							foreignKey.setName(name);
							foreignKey.setReferenceTableName(refTableName);
							table.addForeignKey(foreignKey);
						}

						ForeignKeyFeildModel field = new ForeignKeyFeildModel();
						field.setName(fieldName);

						ForeignKeyFeildModel referenceField = new ForeignKeyFeildModel();
						referenceField.setName(refFieldName);

						foreignKey.addField(field);
						foreignKey.addReferenceField(referenceField);
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
			}

		} finally {
			release(rs);
			release(ps);
		}
	}
}
