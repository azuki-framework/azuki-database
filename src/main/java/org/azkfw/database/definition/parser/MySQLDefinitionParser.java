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

/**
 * @since 1.0.0
 * @version 1.0.0 2015/02/06
 * @author kawakicchi
 */
public class MySQLDefinitionParser extends AbstractDatabaseDefinitionParser {

	/**
	 * コンストラクタ
	 */
	public MySQLDefinitionParser() {
		super(MySQLDefinitionParser.class);
	}

	@Override
	protected String getTableSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.table_schema  AS 'schema' ");
		sql.append("  , A.table_name    AS label ");
		sql.append("  , A.table_name    AS name ");
		sql.append("  , A.table_comment AS comment ");
		sql.append("FROM ");
		sql.append("    information_schema.tables A ");
		sql.append("WHERE ");
		sql.append("    A.table_schema = ? ");
		sql.append(";");
		return sql.toString();
	}

	@Override
	protected String getFieldSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.column_name    AS label ");
		sql.append("  , A.column_name    AS name ");
		sql.append("  , A.column_type    AS type ");
		sql.append("  , A.extra          AS extra ");
		sql.append("  , CASE A.is_nullable WHEN 'NO' THEN true ELSE false END AS notnull ");
		sql.append("  , A.column_default AS 'default' ");
		sql.append("  , A.column_comment AS comment ");
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
		sql.append("    A.index_name  AS name ");
		sql.append("  , A.column_name AS field_name ");
		sql.append("  , CASE A.non_unique WHEN 0 THEN true ELSE false END AS unique_key ");
		sql.append("  , CASE A.index_name WHEN 'PRIMARY' THEN true ELSE false END AS primary_key ");
		sql.append("FROM ");
		sql.append("    information_schema.STATISTICS A ");
		sql.append("WHERE ");
		sql.append("    A.table_schema = ? ");
		sql.append("AND A.table_name   = ? ");
		sql.append("ORDER BY ");
		sql.append("    primary_key DESC ");
		sql.append("  , A.index_name ASC ");
		sql.append("  , A.seq_in_index ASC ");
		sql.append(";");
		return sql.toString();
	}

	@Override
	protected String getForeignKeySQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    A.constraint_name         AS name ");
		sql.append("  , A.column_name             AS field_name ");
		sql.append("  , A.referenced_table_name   AS ref_table_name ");
		sql.append("  , A.referenced_column_name  AS ref_field_name ");
		sql.append("");
		sql.append("FROM ");
		sql.append("    information_schema.KEY_COLUMN_USAGE A ");
		sql.append("WHERE ");
		sql.append("    A.table_schema        = ? ");
		sql.append("AND A.table_name          = ? ");
		sql.append("AND NOT A.constraint_name = 'PRIMARY' ");
		sql.append("ORDER BY ");
		sql.append("    A.constraint_name ");
		sql.append("  , A.ordinal_position ");
		sql.append(";");
		return sql.toString();
	}
}
