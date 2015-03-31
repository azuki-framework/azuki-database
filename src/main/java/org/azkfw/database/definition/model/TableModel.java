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
 * このクラスは、テーブル情報を保持するモデルクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/02/04
 * @author kawakicchi
 */
public final class TableModel {

	/** ラベル */
	private String label;

	/** スキーマ */
	private SchemaModel schema;

	/** テーブル名 */
	private String name;

	/** コメント */
	private String comment;

	/** フィールドリスト */
	private List<FieldModel> fields;

	/** インデックスリスト */
	private List<IndexModel> indexs;

	/** 外部キーリスト */
	private List<ForeignKeyModel> foreignKeys;

	/**
	 * コンストラクタ
	 */
	public TableModel() {
		label = null;
		schema = null;
		name = null;
		comment = null;
		fields = new ArrayList<FieldModel>();
		indexs = new ArrayList<IndexModel>();
		foreignKeys = new ArrayList<ForeignKeyModel>();
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
	 * スキーマを設定する。
	 * 
	 * @param schema スキーマ
	 */
	public void setSchema(final SchemaModel schema) {
		this.schema = schema;
	}

	/**
	 * スキーマを取得する。
	 * 
	 * @return スキーマ
	 */
	public SchemaModel getSchema() {
		return schema;
	}

	/**
	 * テーブル名を設定する。
	 * 
	 * @param name テーブル名
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * テーブル名を取得する。
	 * 
	 * @return テーブル名
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
	 * フィールドを追加する。
	 * 
	 * @param field フィールド
	 */
	public void addField(final FieldModel field) {
		fields.add(field);
	}

	/**
	 * フィールドリストを取得する。
	 * 
	 * @return フィールドリスト
	 */
	public List<FieldModel> getFields() {
		return fields;
	}

	/**
	 * フィールドを取得する。
	 * 
	 * @param name フィールド名
	 * @return フィールド。フィールドが存在しない場合、<code>null</code>を返す。
	 */
	public FieldModel getField(final String name) {
		for (FieldModel field : fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * インデックスを追加する。
	 * 
	 * @param index インデックス
	 */
	public void addIndex(final IndexModel index) {
		indexs.add(index);
	}

	/**
	 * インデックスリストを取得する。
	 * 
	 * @return インデックスリスト
	 */
	public List<IndexModel> getIndexs() {
		return indexs;
	}

	/**
	 * インデックスを取得する。
	 * 
	 * @param name インデックス名
	 * @return インデックス。インデックスが存在しない場合、<code>null</code>を返す。
	 */
	public IndexModel getIndex(final String name) {
		for (IndexModel index : indexs) {
			if (index.getName().equals(name)) {
				return index;
			}
		}
		return null;
	}

	/**
	 * 外部キーを追加する。
	 * 
	 * @param foreignKey 外部キー
	 */
	public void addForeignKey(final ForeignKeyModel foreignKey) {
		this.foreignKeys.add(foreignKey);
	}

	/**
	 * 外部キーリストを取得する。
	 * 
	 * @return 外部キーリスト
	 */
	public List<ForeignKeyModel> getForeignKeys() {
		return foreignKeys;
	}

	/**
	 * 外部キーを取得する。
	 * 
	 * @param name 外部キー名
	 * @return 外部キー。外部キーが存在しない場合、<code>null</code>を返す。
	 */
	public ForeignKeyModel getForeignKey(final String name) {
		for (ForeignKeyModel foreignKey : foreignKeys) {
			if (foreignKey.getName().equals(name)) {
				return foreignKey;
			}
		}
		return null;
	}

	/**
	 * 主キーのインデックスを取得する。
	 * 
	 * @return 主キーが存在しない場合、<code>null</code>を返す。
	 */
	public IndexModel getPrimaryIndex() {
		for (IndexModel index : indexs) {
			if (index.isPrimaryKey()) {
				return index;
			}
		}
		return null;
	}
}
