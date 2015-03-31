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

import java.util.ArrayList;
import java.util.List;

import org.azkfw.database.definition.model.SchemaModel;
import org.azkfw.database.definition.model.TableModel;

/**
 * このクラスは、データベース定義解析のオプション情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/06
 * @author kawakicchi
 */
public class DatabaseDefinitionParserOption {

	/** 対象スキーマ名 */
	private List<String> includeSchemas;
	/** 除外スキーマ名 */
	private List<String> excludeSchemas;

	/** 対象テーブル名 */
	private List<String> includeTables;
	/** 除外テーブル名 */
	private List<String> excludeTables;

	/**
	 * コンストラクタ
	 */
	public DatabaseDefinitionParserOption() {
		includeSchemas = new ArrayList<String>();
		excludeSchemas = new ArrayList<String>();
		includeTables = new ArrayList<String>();
		excludeTables = new ArrayList<String>();
	}

	/**
	 * 対象スキーマを追加する。
	 * 
	 * @param name スキーマ名
	 */
	public void addIncludeSchema(final String name) {
		includeSchemas.add(name);
	}

	/**
	 * 除外スキーマを追加する。
	 * 
	 * @param name スキーマ名
	 */
	public void addExcludeSchema(final String name) {
		excludeSchemas.add(name);
	}

	/**
	 * 対象テーブルを追加する。
	 * 
	 * @param schema スキーマ名
	 * @param name テーブル名
	 */
	public void addIncludeTable(final String schema, final String name) {
		includeTables.add(schema + "." + name);
	}

	/**
	 * 対象テーブルを追加する。
	 * 
	 * @param name テーブル名
	 */
	public void addIncludeTable(final String name) {
		includeTables.add(name);
	}

	/**
	 * 除外テーブルを追加する。
	 * 
	 * @param schema スキーマ名
	 * @param name テーブル名
	 */
	public void addExcludeTable(final String schema, final String name) {
		excludeTables.add(schema + "." + name);
	}

	/**
	 * 除外テーブルを追加する。
	 * 
	 * @param name テーブル名
	 */
	public void addExcludeTable(final String name) {
		excludeTables.add(name);
	}

	/**
	 * スキーマが対象か判断する。
	 * 
	 * @param schema スキーマ
	 * @return 結果
	 */
	public boolean isEnableSchema(final SchemaModel schema) {
		for (String name : excludeSchemas) {
			if (isMatch(name, schema.getName())) {
				return false;
			}
		}

		if (0 == includeSchemas.size()) {
			return true;
		} else {
			for (String name : includeSchemas) {
				if (isMatch(name, schema.getName())) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * テーブルが対象か判断する。
	 * 
	 * @param table テーブル
	 * @return 結果
	 */
	public boolean isEnableTable(final TableModel table) {
		for (String name : excludeTables) {
			int index = name.indexOf(".");
			if (-1 != index) {
				String schemaName = name.substring(0, index);
				if (isMatch(schemaName, table.getSchema().getName())) {
					String tableName = name.substring(index + 1);
					if (isMatch(tableName, table.getName())) {
						return false;
					}
				}
			} else {
				if (isMatch(name, table.getName())) {
					return false;
				}
			}
		}

		if (0 == includeTables.size()) {
			return true;
		} else {
			for (String name : includeTables) {
				int index = name.indexOf(".");
				if (-1 != index) {
					String schemaName = name.substring(0, index);
					if (isMatch(schemaName, table.getSchema().getName())) {
						String tableName = name.substring(index + 1);
						if (isMatch(tableName, table.getName())) {
							return true;
						}
					}
				} else {
					if (isMatch(name, table.getName())) {
						return true;
					}
				}
			}
			return false;
		}

	}

	private static boolean isMatch(final String a, final String b) {
		if (null == a && null == b) {
			return true;
		} else if (null == a || null == b) {
			return false;
		} else {
			return a.toLowerCase().equals(b.toLowerCase());
		}
	}

}
