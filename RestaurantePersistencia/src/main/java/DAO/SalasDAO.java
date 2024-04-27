/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.Conexion;
import DTO.SalasDTO;
import Interfaces.ISalasDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class SalasDAO implements ISalasDAO {

    
    private static final SalasDAO instance = new SalasDAO();
    
    
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Conexion cn = Conexion.obtenerInstancia();
    
    
    private SalasDAO() {
    }

    // Método estático para obtener la instancia Singleton
    public static SalasDAO getInstance() {
        return instance;
    }
    
    

    @Override
    public boolean RegistrarSala(SalasDTO sl) {
        String sql = "INSERT INTO salas(nombre, mesas) VALUES (?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sl.getNombre());
            ps.setInt(2, sl.getMesas());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public List Listar() {
        List<SalasDTO> Lista = new ArrayList();
        String sql = "SELECT * FROM salas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SalasDTO sl = new SalasDTO();
                sl.setId(rs.getInt("id"));
                sl.setNombre(rs.getString("nombre"));
                sl.setMesas(rs.getInt("mesas"));
                Lista.add(sl);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    @Override
    public boolean Eliminar(int id) {
        String sql = "DELETE FROM salas WHERE id = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public boolean Modificar(SalasDTO sl) {
        String sql = "UPDATE salas SET nombre=?, mesas=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sl.getNombre());
            ps.setInt(2, sl.getMesas());
            ps.setInt(3, sl.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
           cerrarRecursos();
        }
    }

    
    
    
    private void cerrarRecursos() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
