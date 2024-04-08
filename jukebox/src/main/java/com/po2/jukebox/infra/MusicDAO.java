/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.po2.jukebox.infra;

import com.po2.jukebox.domain.Music;
import java.sql.Connection;
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
public class MusicDAO {

    static Connection conn;
    static PreparedStatement ps;
    static ResultSet rs;

    public static Music read(int id) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("SELECT * FROM music WHERE id = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Music(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MusicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static int update(Music m) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("UPDATE music SET name = ?, link = ?, duration = ?, band_name = ? WHERE id = ?;");
            ps.setString(1, m.getName());
            ps.setString(2, m.getLink());
            ps.setInt(3, m.getDurationInSeconds());
            ps.setString(4, m.getBandName());
            ps.setInt(5, m.getId());

            int rowCount = ps.executeUpdate();

            conn.close();

            return rowCount;
        } catch (SQLException ex) {
            Logger.getLogger(MusicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static int insert(Music m) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("INSERT INTO music (name, link, duration, band_name) VALUES (?, ?, ?, ?);");
            ps.setString(1, m.getName());
            ps.setString(2, m.getLink());
            ps.setInt(3, m.getDurationInSeconds());
            ps.setString(4, m.getBandName());

            int rowCount = ps.executeUpdate();

            conn.close();

            return rowCount;
        } catch (SQLException ex) {
            Logger.getLogger(MusicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static int remove(int id) {
        conn = Database.getConnection();

        try {
            ps = conn.prepareStatement("DELETE FROM music WHERE id = ?;");
            ps.setInt(1, id);

            int rowCount = ps.executeUpdate();

            conn.close();

            return rowCount;
        } catch (SQLException ex) {
            Logger.getLogger(MusicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static ArrayList<Music> list() {
        conn = Database.getConnection();
        ArrayList<Music> musics = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM music;");
            rs = ps.executeQuery();

            while (rs.next()) {
                musics.add(new Music(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MusicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return musics;
    }

}
