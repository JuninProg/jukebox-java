/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.po2.jukebox.infra;

import com.po2.jukebox.domain.Band;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author knoba
 */
public class BandDAO {

    static Connection conn;
    static PreparedStatement ps;
    static ResultSet rs;

    public static int update(Band b, String newName) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("UPDATE band SET name = ?, gender = ?, creation_date = ? WHERE name = ?;");
            ps.setString(1, newName != null ? newName : b.getName());
            ps.setString(2, b.getGender());
            ps.setDate(3, Date.valueOf(b.getCreationDate()));
            ps.setString(4, b.getName());

            int rowCount = ps.executeUpdate();

            conn.close();

            return rowCount;
        } catch (SQLException ex) {
            Logger.getLogger(BandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static Band read(String name) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("SELECT * FROM band WHERE name = ?;");
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Band(rs.getString(1), rs.getDate(2).toLocalDate(), rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static int insert(Band b) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("INSERT INTO band (name, gender, creation_date) VALUES (?, ?, ?);");
            ps.setString(1, b.getName());
            ps.setString(2, b.getGender());
            ps.setDate(3, Date.valueOf(b.getCreationDate()));

            int rowCount = ps.executeUpdate();

            conn.close();

            return rowCount;
        } catch (SQLException ex) {
            Logger.getLogger(BandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static ArrayList<Band> list() {
        conn = Database.getConnection();
        ArrayList<Band> bands = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM band;");
            rs = ps.executeQuery();

            while (rs.next()) {
                bands.add(new Band(rs.getString(1), rs.getDate(2).toLocalDate(), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bands;
    }

    public static int remove(String name) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("DELETE FROM band WHERE name = ?;");
            ps.setString(1, name);

            int rowCount = ps.executeUpdate();

            conn.close();

            return rowCount;
        } catch (SQLException ex) {
            Logger.getLogger(BandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
