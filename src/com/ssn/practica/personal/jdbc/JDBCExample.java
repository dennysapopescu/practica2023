package com.ssn.practica.personal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "my_user",
				"my_user")) {
			if (conn != null) {
				System.out.println("Connection successful");
			}

			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from vw_note_studenti");

			while (resultSet.next()) {
				System.out.println(//
						resultSet.getInt(1) + " " + //
								resultSet.getString(2) + " " + //
								resultSet.getDouble(3) + " " + //
								resultSet.getInt(4) //
				);
			}

			resultSet.close();
		}

	}
}
