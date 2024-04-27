
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Carlo
 */
public class Conexion {
   private static Conexion instancia;
    private Connection con;

    private Conexion() {
        
    }

    public static Conexion obtenerInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection(){
        try {
            if (con == null || con.isClosed()) {
                String myBD = "jdbc:mysql://localhost:3306/restaurante?serverTimezone=UTC";
                con = DriverManager.getConnection(myBD, "root", "Tequida11");
            }
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    
    
    
}
