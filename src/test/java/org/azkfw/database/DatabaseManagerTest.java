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
package org.azkfw.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.azkfw.database.DatabaseManager;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Kawakicchi
 *
 */
public class DatabaseManagerTest extends TestCase {

	@Test
	public void test() {

		Connection con = null;
		try {
			DatabaseManager.getInstance().load(new File("src/test/data/database.xml"));
			
			con = DatabaseManager.getInstance().getConnection("AA");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from tm_host");
			while (rs.next()) {
				System.out.println("Id:" + rs.getString("host_id") + " Name:" + rs.getString("host_name"));
			}
			rs.close();
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

}
