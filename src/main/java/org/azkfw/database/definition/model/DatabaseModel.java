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
package org.azkfw.database.definition.model;

import java.util.ArrayList;
import java.util.List;

/**
 * このクラスは、データベース情報を保持するモデルクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/05
 * @author kawakicchi
 */
public class DatabaseModel {

	/** テーブルリスト */
	private List<TableModel> tables;

	/**
	 * コンストラクタ
	 */
	public DatabaseModel() {
		tables = new ArrayList<TableModel>();
	}

	/**
	 * テーブルを追加する。
	 * 
	 * @param table テーブル
	 */
	public void addTable(final TableModel table) {
		this.tables.add(table);
	}

	/**
	 * テーブルリストを取得する。
	 * 
	 * @return テーブルリスト
	 */
	public List<TableModel> getTables() {
		return tables;
	}

	/**
	 * テーブルを取得する。
	 * 
	 * @param name テーブル名
	 * @return テーブル。テーブルが存在しない場合、<code>null</code>を返す。
	 */
	public TableModel getTable(final String name) {
		for (TableModel table : tables) {
			if (table.getName().equals(name)) {
				return table;
			}
		}
		return null;
	}
}
