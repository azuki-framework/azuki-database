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

/**
 * このクラスは、外部キーフィールド情報を保持するモデルクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/04
 * @author kawakicchi
 */
public class ForeignKeyFeildModel {

	/** フィールド名 */
	private String name;

	/**
	 * コンストラクタ
	 */
	public ForeignKeyFeildModel() {
		name = null;
	}

	/**
	 * フィールド名を設定する。
	 * 
	 * @param name フィールド名
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * フィールド名を取得する。
	 * 
	 * @return フィールド名
	 */
	public String getName() {
		return name;
	}

}
