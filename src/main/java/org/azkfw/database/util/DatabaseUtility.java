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
package org.azkfw.database.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kawakicchi
 *
 */
public class DatabaseUtility {

	private DatabaseUtility() {

	}

	/**
	 * {@link Connection} を解放する。
	 * 
	 * @param connections {@link Connection}
	 */
	public static void release(final Connection... connections) {
		for (Connection connection : connections) {
			if (null != connection) {
				try {
					if (!connection.isClosed()) {
						connection.close();
					}
				} catch (SQLException ex) {
					//
				}
			}
		}
	}

	/**
	 * {@link ResultSet} を解放する。
	 * 
	 * @param resultSets {@link ResultSet}
	 */
	public static void release(final ResultSet... resultSets) {
		for (ResultSet rs : resultSets) {
			if (null != rs) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException ex) {
					//
				}
			}
		}
	}

	/**
	 * {@link PreparedStatement} を解放する。
	 * 
	 * @param preparedStatements {@link PreparedStatement}
	 */
	public static void release(final PreparedStatement... preparedStatements) {
		for (PreparedStatement ps : preparedStatements) {
			if (null != ps) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException ex) {
					//
				}
			}
		}
	}
}
