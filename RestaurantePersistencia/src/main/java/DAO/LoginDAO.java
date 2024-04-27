package DAO;

import Conexion.Conexion;
import DTO.ConfigDTO;
import DTO.LoginDTO;
import Interfaces.ILoginDAO;
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
public class LoginDAO implements ILoginDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Conexion cn = Conexion.obtenerInstancia();
    
    
    
     private static LoginDAO instance;

    // Constructor privado para evitar la instanciación directa
    private LoginDAO() {}

    // Método estático para obtener la instancia única de PlatosDAO
    public static LoginDAO getInstance() {
        if (instance == null) {
            instance = new LoginDAO();
        }
        return instance;
    }

    @Override
    public LoginDTO log(String usuario, String pass) {
        LoginDTO l = new LoginDTO();
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND pass = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setUsuario(rs.getString("usuario"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }

    @Override
    public boolean Registrar(LoginDTO reg) {
        String sql = "INSERT INTO usuarios (nombre, usuario, pass, rol) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getUsuario());
            ps.setString(3, reg.getPass());
            ps.setString(4, reg.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public List ListarUsuarios() {
        List<LoginDTO> Lista = new ArrayList();
        String sql = "SELECT * FROM usuarios";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                LoginDTO lg = new LoginDTO();
                lg.setId(rs.getInt("id"));
                lg.setNombre(rs.getString("nombre"));
                lg.setUsuario(rs.getString("usuario"));
                lg.setRol(rs.getString("rol"));
                Lista.add(lg);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    @Override
    public boolean ModificarDatos(ConfigDTO conf) {
        String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, conf.getRuc());
            ps.setString(2, conf.getNombre());
            ps.setString(3, conf.getTelefono());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getMensaje());
            ps.setInt(6, conf.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public ConfigDTO datosEmpresa() {
        ConfigDTO conf = new ConfigDTO();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }

}
