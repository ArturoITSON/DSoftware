/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Conexion.ConexionBD;
import DTOs.MenuDTO;
import DTOs.PlatilloDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
        
/**
 *
 * @author Ruben
 */
public class MenuDAO {
    public MenuDTO obtenerMenu() {
        MenuDTO menu = null;
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu ORDER BY fecha DESC LIMIT 1")) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    menu = new MenuDTO();
                    menu.setId(rs.getInt("id"));
                    menu.setFecha(rs.getDate("fecha"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }
    
    
   public void actualizarMenu(List<PlatilloDTO> platillosDestacados) {
        MenuDTO menu = obtenerMenu();
        if (menu != null) {
            try (Connection conn = ConexionBD.obtenerConexion()) {
                // Eliminamos los platillos destacados actuales del menú
                try (PreparedStatement stmtDelete = conn.prepareStatement("DELETE FROM platillo WHERE menu_id = ?")) {
                    stmtDelete.setInt(1, menu.getId());
                    stmtDelete.executeUpdate();
                }

                // Insertamos los nuevos platillos destacados
                try (PreparedStatement stmtInsert = conn.prepareStatement("INSERT INTO platillo (menu_id, nombre, ingredientes, categoria, precio) VALUES (?, ?, ?, ?, ?)")) {
                    for (PlatilloDTO platillo : platillosDestacados) {
                        stmtInsert.setInt(1, menu.getId());
                        stmtInsert.setString(2, platillo.getNombre());
                        stmtInsert.setString(3, platillo.getIngredientes());
                        stmtInsert.setString(4, platillo.getCategoria());
                        stmtInsert.setFloat(5, platillo.getPrecio());
                        stmtInsert.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se pudo encontrar un menú existente para actualizar.");
        }
    }
}