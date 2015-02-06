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
 * このクラスは、インデックス情報を保持するモデルクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/04
 * @author kawakicchi
 */
public class IndexModel {

	/** インデックス名 */
	private String name;

	/** 主キーフラグ */
	private boolean primaryKeyFlag;

	/** ユニークフラグ */
	private boolean uniqueFlag;

	/** フィールドリスト */
	private List<IndexFieldModel> fields;

	/** コメント */
	private String comment;

	/**
	 * コンストラクタ
	 */
	public IndexModel() {
		name = null;
		primaryKeyFlag = false;
		uniqueFlag = false;
		fields = new ArrayList<IndexFieldModel>();
		comment = null;
	}

	/**
	 * インデックス名を設定する。
	 * 
	 * @param name インデックス名
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * インデックス名を取得する。
	 * 
	 * @return インデックス名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 主キー有無を設定する。
	 * 
	 * @param flag 有無
	 */
	public void setPrimaryKey(final boolean flag) {
		this.primaryKeyFlag = flag;
	}

	/**
	 * 主キー有無を判断する。
	 * 
	 * @return 判断
	 */
	public boolean isPrimaryKey() {
		return primaryKeyFlag;
	}

	/**
	 * ユニーク有無を設定する。
	 * 
	 * @param flag 有無
	 */
	public void setUnique(final boolean flag) {
		this.uniqueFlag = flag;
	}

	/**
	 * ユニーク有無を判断する。
	 * 
	 * @return 判断
	 */
	public boolean isUnique() {
		return uniqueFlag;
	}

	/**
	 * フィールドを追加する。
	 * 
	 * @param field フィールド
	 */
	public void addField(final IndexFieldModel field) {
		fields.add(field);
	}

	/**
	 * フィールドリストを取得する。
	 * 
	 * @return フィールドリスト
	 */
	public List<IndexFieldModel> getFields() {
		return fields;
	}

	/**
	 * フィールドを取得する。
	 * 
	 * @param name フィールド名
	 * @return フィールド。フィールドが存在しない場合、<code>null</code>を返す。
	 */
	public IndexFieldModel getField(final String name) {
		for (IndexFieldModel field : fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * コメントを設定する。
	 * 
	 * @param comment コメント
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}

	/**
	 * コメントを取得する。
	 * 
	 * @return コメント
	 */
	public String getComment() {
		return comment;
	}
}
