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

/**
 * このクラスは、PostgreSQLデータベースを定義したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/03/03
 * @author kawakicchi
 */
public class PostgreSQLDefinition extends AbstractDatabaseDefinition {

	public PostgreSQLDefinition(final Connection connection) {
		super(PostgreSQLDefinition.class, connection);
	}

	@Override
	protected String getSchemaSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.schema_name AS name ");
		sql.append("FROM ");
		sql.append("    information_schema.schemata A ");
		sql.append("WHERE ");
		sql.append("    NOT schema_name like 'pg_%' ");
		sql.append("AND NOT schema_name in ('information_schema') ");
		sql.append("ORDER BY ");
		sql.append("    A.schema_name ");
		sql.append(";");
		return sql.toString();
	}

	@Override
	protected String getTableListSQL() {
		return getTableSQL(false);
	}

	@Override
	protected String getTableSQL() {
		return getTableSQL(true);
	}

	private String getTableSQL(final boolean table) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.table_schema   AS schema ");
		sql.append("  , A.table_name     AS label ");
		sql.append("  , A.table_name     AS name ");
		sql.append("  , ''               AS comment ");
		sql.append("FROM ");
		sql.append("    information_schema.tables A ");
		sql.append("WHERE ");
		sql.append("    A.table_schema = ? ");
		if (table) {
			sql.append("AND A.table_name = ? ");
		}
		sql.append("ORDER BY ");
		sql.append("    A.table_name ");
		sql.append(";");
		return sql.toString();
	}

	@Override
	protected String getFieldSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.column_name    AS label ");
		sql.append("  , A.column_name    AS name ");
		sql.append("  , A.data_type      AS type ");
		sql.append("  , ''               AS extra ");
		sql.append("  , CASE A.is_nullable WHEN 'NO' THEN true ELSE false END AS notnull ");
		sql.append("  , A.column_default AS default ");
		sql.append("  , ''               AS comment ");
		sql.append("FROM ");
		sql.append("    information_schema.columns A ");
		sql.append("WHERE ");
		sql.append("    A.table_schema = ? ");
		sql.append("AND A.table_name   = ? ");
		sql.append("ORDER BY ");
		sql.append("    A.ordinal_position ");
		sql.append(";");
		return sql.toString();
	}

	@Override
	protected String getIndexSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.table_catalog ");
		sql.append("  , A.table_schema ");
		sql.append("  , A.table_name ");
		sql.append("  , kcu.constraint_name AS name ");
		sql.append("  , kcu.column_name     AS field_name ");
		sql.append("  , kcu.ordinal_position ");
		sql.append("  , tc.constraint_type ");
		sql.append("  , true AS  unique_key ");
		sql.append("  , true AS  primary_key ");
		sql.append("FROM ");
		sql.append("    INFORMATION_SCHEMA.TABLES A ");
		sql.append("    LEFT JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc ");
		sql.append("    ON tc.table_catalog = A.table_catalog ");
		sql.append("    AND tc.table_schema = A.table_schema");
		sql.append("    AND tc.table_name = A.table_name");
		sql.append("    AND tc.constraint_type = 'PRIMARY KEY' ");
		sql.append("    ");
		sql.append("    ");
		sql.append("    LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu ");
		sql.append("    ON kcu.table_catalog = tc.table_catalog ");
		sql.append("    AND kcu.table_schema = tc.table_schema ");
		sql.append("    AND kcu.table_name = tc.table_name ");
		sql.append("    AND kcu.constraint_name = tc.constraint_name ");
		sql.append("    ");
		sql.append("WHERE ");
		sql.append("    A.table_schema NOT IN ('pg_catalog', 'information_schema') ");
		sql.append("AND A.table_schema = ? ");
		sql.append("AND A.table_name   = ? ");
		sql.append("    ");
		sql.append("ORDER BY ");
		sql.append("    A.table_catalog ");
		sql.append("  , A.table_schema ");
		sql.append("  , A.table_name ");
		sql.append("  , kcu.constraint_name ");
		sql.append("  , kcu.ordinal_position ");
		sql.append("");
		return sql.toString();
	}

	@Override
	protected String getForeignKeySQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.table_catalog ");
		sql.append("  , A.table_schema ");
		sql.append("  , A.table_name ");
		sql.append("  , kcu.constraint_name AS name ");
		sql.append("  , kcu.column_name     AS field_name ");
		sql.append("  , kcu.ordinal_position ");
		sql.append("  , tc.constraint_type ");
		sql.append("  , '' AS  ref_table_name ");
		sql.append("  , '' AS  ref_field_name ");
		sql.append("FROM ");
		sql.append("    INFORMATION_SCHEMA.TABLES A ");
		sql.append("    LEFT JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc ");
		sql.append("    ON tc.table_catalog = A.table_catalog ");
		sql.append("    AND tc.table_schema = A.table_schema");
		sql.append("    AND tc.table_name = A.table_name");
		sql.append("    AND tc.constraint_type = 'FOREIGN KEY' ");
		sql.append("    ");
		sql.append("    ");
		sql.append("    LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu ");
		sql.append("    ON kcu.table_catalog = tc.table_catalog ");
		sql.append("    AND kcu.table_schema = tc.table_schema ");
		sql.append("    AND kcu.table_name = tc.table_name ");
		sql.append("    AND kcu.constraint_name = tc.constraint_name ");
		sql.append("    ");
		sql.append("WHERE ");
		sql.append("    A.table_schema NOT IN ('pg_catalog', 'information_schema') ");
		sql.append("AND A.table_schema = ? ");
		sql.append("AND A.table_name   = ? ");
		sql.append("    ");
		sql.append("ORDER BY ");
		sql.append("    A.table_catalog ");
		sql.append("  , A.table_schema ");
		sql.append("  , A.table_name ");
		sql.append("  , kcu.constraint_name ");
		sql.append("  , kcu.ordinal_position ");
		sql.append("");
		return sql.toString();
	}
}
