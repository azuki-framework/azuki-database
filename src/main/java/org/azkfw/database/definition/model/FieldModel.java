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
 * このクラスは、フィールド情報を保持するモデルクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/04
 * @author kawakicchi
 */
public class FieldModel {

	/** ラベル */
	private String label;

	/** フィールド名 */
	private String name;

	/** コメント */
	private String comment;

	/** タイプ */
	private FieldTypeModel type;

	/** 付加情報 */
	private String extra;

	/** NotNull制約有無フラグ */
	private boolean notNullFlag;

	/** デフォルト値有無フラグ */
	private boolean defaultFlag;

	/** デフォルト値 */
	private Object defaultValue;

	/**
	 * コンストラクタ
	 */
	public FieldModel() {
		label = null;
		name = null;
		comment = null;
		type = null;
		extra = null;
		notNullFlag = false;
		defaultFlag = false;
		defaultValue = null;
	}

	/**
	 * ラベルを設定する。
	 * 
	 * @param label ラベル
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * ラベルを取得する。
	 * 
	 * @return ラベル
	 */
	public String getLabel() {
		return label;
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

	/**
	 * タイプを設定する。
	 * 
	 * @param タイプ
	 */
	public void setType(final FieldTypeModel type) {
		this.type = type;
	}

	/**
	 * タイプを取得する。
	 * 
	 * @return タイプ
	 */
	public FieldTypeModel getType() {
		return type;
	}

	/**
	 * 付加情報を設定する。
	 * 
	 * @param extra 付加情報
	 */
	public void setExtra(final String extra) {
		this.extra = extra;
	}

	/**
	 * 付加情報を取得する。
	 * 
	 * @return 付加情報
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * NotNull制約を設定する。
	 * 
	 * @param flag フラグ
	 */
	public void setNotNull(final boolean flag) {
		this.notNullFlag = flag;
	}

	/**
	 * NotNull制約を取得する。
	 * 
	 * @return フラグ
	 */
	public boolean isNotNull() {
		return notNullFlag;
	}

	/**
	 * デフォルト値の有無を設定する。
	 * 
	 * @param flag フラグ
	 */
	public void setDefaultFlag(final boolean flag) {
		this.defaultFlag = flag;
	}

	/**
	 * デフォルト値の有無を判断する。
	 * 
	 * @return 判断
	 */
	public boolean isDefaultFlag() {
		return defaultFlag;
	}

	/**
	 * デフォルト値を設定する。
	 * 
	 * @param value デフォルト値
	 */
	public void setDefaultValue(final Object value) {
		this.defaultValue = value;
	}

	/**
	 * デフォルト値を取得する。
	 * 
	 * @return 　デフォルト値
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}
}
