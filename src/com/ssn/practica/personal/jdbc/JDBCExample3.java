package com.ssn.practica.personal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCExample3 {

    private static void inserarePret(Connection conn, int idArt, int idMag, float pret) throws SQLException {
        PreparedStatement statementPr = conn.prepareStatement("insert into preturi values (?, ?, ?)");
        statementPr.setInt(1, idArt);
        statementPr.setInt(2, idMag);
        statementPr.setFloat(3, pret);
        statementPr.execute();
    }

    private static void inserareMag(Connection conn, int id, String name) throws SQLException {
        PreparedStatement statementMag = conn.prepareStatement("insert into magazin values (?, ?)");
        statementMag.setInt(1, id);
        statementMag.setString(2, name);
        statementMag.execute();
    }

    private static void inserareArt(Connection conn, int id, String name) throws SQLException {
        PreparedStatement statementArt = conn.prepareStatement("insert into articol values (?, ?)");
        statementArt.setInt(1, id);
        statementArt.setString(2, name);
        statementArt.execute();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "my_user",
                "my_user")) {
            if (conn != null) {
                System.out.println("Connection successful");
            }

            // in articol
            inserareArt(conn, 1, "Articol1");
            inserareArt(conn, 2, "Articol2");
            inserareArt(conn, 3, "Articol3");

            // pentru magazin
            inserareMag(conn, 1, "Magazin1");
            inserareMag(conn, 2, "Magazin2");
            inserareMag(conn, 3, "Magazin3");

            // pentru preturi
            inserarePret(conn, 1, 1, 10.99f);
            inserarePret(conn, 1, 2, 11.99f);
            inserarePret(conn, 1, 3, 271.99f);
            inserarePret(conn, 2, 1, 1144.99f);
            inserarePret(conn, 2, 2, 11.00f);
            inserarePret(conn, 2, 3, 113.87f);
            inserarePret(conn, 3, 1, 112.33f);
            inserarePret(conn, 3, 2, 1144.99f);
            inserarePret(conn, 3, 3, 1.79f);

            System.out.println("Datele au fost inserate");

            // afisare
            
            String query = "SELECT a.nume, m.nume AS nume_magazin, p.pret " +
                    "FROM articol a " +
                    "JOIN preturi p ON a.id = p.id_articol " +
                    "JOIN magazin m ON p.id_magazin = m.id " +
                    "WHERE p.pret = (SELECT MIN(pret) FROM preturi WHERE id_articol = p.id_articol) " +
                    "ORDER BY a.nume";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String numeArt = rs.getString("nume");
                String numeMag = rs.getString("nume_magazin");
                Float pret = rs.getFloat("pret");
                System.out.println("Articol: " + numeArt + ", Magazin: " + numeMag + ", Pret: " + pret);
            }
            
        }
    }
}
