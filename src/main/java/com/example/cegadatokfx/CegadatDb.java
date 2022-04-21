package com.example.cegadatokfx;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;

public class CegadatDb {
    //1. Connection osztály példányosítása
    Connection conn;
//2. SQLException and establish connector: getConnection
    public CegadatDb() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cegadatok", "root", "");
    }
//3. ListaFeltolt metódus, SQLExceptionnal
    public ArrayList<CegadatRekord> ListaFeltolt() throws SQLException {
        //új Cegadatlista létrehozása
        ArrayList<CegadatRekord> lista = new ArrayList<>();
        //statement változó definiálás
        Statement stmt = conn.createStatement();
        //sql változó definiálás
        String sql = "SELECT * FROM alkalmazottak;";
        //result definiálás
        ResultSet result = stmt.executeQuery(sql);
        // lista feltöltése amíg van sql-ben adat
        while (result.next()) {
            int az = result.getInt("az");
            String nev = result.getString("nev");
            int nem = result.getInt("nem");
            Date szuldatum = result.getDate("szuldatum");
            String varos = result.getString("varos");
            String cim = result.getString("cim");
            String telefon = result.getString("telefon");
            int kereset = result.getInt("kereset");

            CegadatRekord elem = new CegadatRekord(az, nev, nem, szuldatum, varos, cim, telefon, kereset);
            lista.add(elem);
        }
        return lista;
    }
    public int rekordHozzaadasa(CegadatRekord pr) throws SQLException {
        String sql = "INSERT INTO alkalmazottak(nev, nem, szuldatum, varos, cim, telefon, kereset) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pr.getNev());
        stmt.setInt(2, pr.getNem());
        stmt.setDate(3, pr.getSzuldatum());
        stmt.setString(4, pr.getVaros());
        stmt.setString(5, pr.getCim());
        stmt.setString(6, pr.getTelefon());
        stmt.setInt(7, pr.getKereset());

        return stmt.executeUpdate();
    }
    public boolean rekordTorlese(int az) throws SQLException {  //Update ugyan ez
        String sql = "DELETE FROM alkalmazottak WHERE az = ?";
        //System.out.println(sql);
        //System.out.println(az);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, az);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean rekordUpdate(int az, CegadatRekord pr) throws SQLException {  //Update ugyan ez
        String sql = "UPDATE alkalmazottak WHERE az = ?";
        System.out.println(sql);
        System.out.println(az);
        PreparedStatement stmt = conn.prepareStatement(sql);
        //stmt.executeUpdate("INSERT INTO alkalmazottak VALUES(" + pr.getNev() + ",'" + pr.getNem() + "'," + pr.getSzuldatum()+ "'," + pr.getVaros()+ "'," + pr.getCim()+ "'," + pr.getTelefon()+ "'," + pr.getKereset() + ")");

        stmt.setString(1, pr.getNev());
        stmt.setInt(2, pr.getNem());
        stmt.setDate(3, pr.getSzuldatum());
        stmt.setString(4, pr.getVaros());
        stmt.setString(5, pr.getCim());
        stmt.setString(6, pr.getTelefon());
        stmt.setInt(7, pr.getKereset());
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}