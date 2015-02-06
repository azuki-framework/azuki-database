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
 * このクラスは、外部キー情報を保持するモデルクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/04
 * @author kawakicchi
 */
public class ForeignKeyModel {

	/** 外部キー名 */
	private String name;

	/** フィールドリスト */
	private List<ForeignKeyFeildModel> fields;

	/** 参照テーブル名 */
	private String referenceTableName;

	/** 参照フィールドリスト */
	private List<ForeignKeyFeildModel> referenceFields;

	/**
	 * コンストラクタ
	 */
	public ForeignKeyModel() {
		name = null;
		fields = new ArrayList<ForeignKeyFeildModel>();
		referenceTableName = null;
		referenceFields = new ArrayList<ForeignKeyFeildModel>();
	}

	/**
	 * 外部キー名を設定する。
	 * 
	 * @param name 外部キー名
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * 外部キー名を取得する。
	 * 
	 * @return 外部キー名
	 */
	public String getName() {
		return name;
	}

	/**
	 * フィールドを追加する。
	 * 
	 * @param field フィールド
	 */
	public void addField(final ForeignKeyFeildModel field) {
		this.fields.add(field);
	}

	/**
	 * フィールドリストを取得する。
	 * 
	 * @return リスト
	 */
	public List<ForeignKeyFeildModel> getFields() {
		return fields;
	}

	/**
	 * 参照テーブル名を設定する。
	 * 
	 * @param name テーブル名
	 */
	public void setReferenceTableName(final String name) {
		referenceTableName = name;
	}

	/**
	 * 参照テーブル名を取得する。
	 * 
	 * @return テーブル名
	 */
	public String getReferenceTableName() {
		return referenceTableName;
	}

	/**
	 * 参照フィールドを追加する。
	 * 
	 * @param field フィールド
	 */
	public void addReferenceField(final ForeignKeyFeildModel field) {
		referenceFields.add(field);
	}

	/**
	 * 参照フィールドリストを取得する。
	 * 
	 * @return フィールド
	 */
	public List<ForeignKeyFeildModel> getReferenceFields() {
		return referenceFields;
	}
}
