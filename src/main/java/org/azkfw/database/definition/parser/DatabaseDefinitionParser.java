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
import java.sql.SQLException;

import org.azkfw.database.definition.model.DatabaseModel;

/**
 * このインターフェースは、データベース定義解析を定義する為のインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/06
 * @author kawakicchi
 */
public interface DatabaseDefinitionParser {

	/**
	 * 解析オプションを設定する。
	 * 
	 * @param option オプション
	 */
	public void setOption(final DatabaseDefinitionParserOption option);

	/**
	 * リスナーを追加する。
	 * 
	 * @param listener リスナー
	 */
	public void addListener(final DatabaseDefinitionParserListener listener);

	/**
	 * リスナーを削除する。
	 * 
	 * @param listener リスナー
	 */
	public void removeListener(final DatabaseDefinitionParserListener listener);

	/**
	 * データベース定義を解析する。
	 * 
	 * @param driver ドライバ名
	 * @param url 接続URL
	 * @param user　ユーザ
	 * @param password パスワード
	 * @return データベース情報
	 */
	public DatabaseModel parse(final String driver, final String url, final String user, final String password);

	/**
	 * データベース定義を解析する。
	 * 
	 * @param connection コネクション情報
	 * @return データベース情報
	 * @throws SQLException SQL操作に起因する問題が発生した場合
	 */
	public DatabaseModel parse(final Connection connection) throws SQLException;
}
