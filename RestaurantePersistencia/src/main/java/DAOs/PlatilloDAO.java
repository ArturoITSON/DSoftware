/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author Ruben
 */
import Conexion.ConexionBD;
import DTOs.PlatilloDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlatilloDAO {
    public void crearPlatillo(PlatilloDTO platillo) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO platillo (nombre, ingredientes, categoria, precio) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, platillo.getNombre());
            stmt.setString(2, platillo.getIngredientes());
            stmt.setString(3, platillo.getCategoria());
            stmt.setFloat(4, platillo.getPrecio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlatilloDTO obtenerPlatillo(int id) {
        PlatilloDTO platillo = null;
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM platillo WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    platillo = new PlatilloDTO();
                    platillo.setId(rs.getInt("id"));
                    platillo.setNombre(rs.getString("nombre"));
                    platillo.setIngredientes(rs.getString("ingredientes"));
                    platillo.setCategoria(rs.getString("categoria"));
                    platillo.setPrecio(rs.getFloat("precio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platillo;
    }

    public void modificarPlatillo(PlatilloDTO platillo) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("UPDATE platillo SET nombre = ?, ingredientes = ?, categoria = ?, precio = ? WHERE id = ?")) {
            stmt.setString(1, platillo.getNombre());
            stmt.setString(2, platillo.getIngredientes());
            stmt.setString(3, platillo.getCategoria());
            stmt.setFloat(4, platillo.getPrecio());
            stmt.setInt(5, platillo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

