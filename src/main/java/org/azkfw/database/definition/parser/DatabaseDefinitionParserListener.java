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
 * このインターフェースは、データベース定義解析のイベントを定義したリスナです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/06
 * @author kawakicchi
 */
public interface DatabaseDefinitionParserListener {

	/**
	 * 解析開始前に呼び出されるイベント。
	 * 
	 * @param event イベント情報
	 */
	public void databaseDefinitionParserStarted(final DatabaseDefinitionParserEvent event);

	/**
	 * 解析終了後に呼び出されるイベント。
	 * 
	 * @param event イベント情報
	 */
	public void databaseDefinitionParserFinished(final DatabaseDefinitionParserEvent event);
}
