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

import java.sql.SQLException;
import java.util.List;

import org.azkfw.database.definition.model.SchemaModel;
import org.azkfw.database.definition.model.TableModel;

/**
 * このインターフェースは、データーベース定義機能を表現するインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/03/03
 * @author kawakicchi
 */
public interface DatabaseDefinition {

	/**
	 * スキーマ情報一覧を取得する 。
	 * 
	 * @return スキーマ情報一覧
	 * @throws SQLException SQL操作に起因する問題が発生した場合
	 */
	public List<SchemaModel> getSchemaList() throws SQLException;

	/**
	 * スキーマに属するテーブル情報一覧を取得する。
	 * 
	 * @param schema スキーマ情報
	 * @return テーブル情報一覧
	 * @throws SQLException SQL操作に起因する問題が発生した場合
	 */
	public List<TableModel> getTableList(final SchemaModel schema) throws SQLException;

	/**
	 * スキーマに属する指定テーブルのテーブル情報を取得する。
	 * 
	 * @param schema スキーマ情報
	 * @param tableName テーブル名
	 * @return テーブル情報
	 * @throws SQLException SQL操作に起因する問題が発生した場合
	 */
	public TableModel getTable(final SchemaModel schema, final String tableName) throws SQLException;
}
