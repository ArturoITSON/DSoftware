/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Conexion.Conexion;
import Conexion.IConexionBD;
import DAO.UsuarioDAO;
import DAO.PedidosDAO;
import DAO.PlatosDAO;
import DAO.SalasDAO;

import Interfaces.IPedidosDAO;
import Interfaces.IPlatosDAO;
import Interfaces.ISalasDAO;
import Interfaces.IUsuarioDAO;

/**
 *
 * @author Carlo
 */
public class Factory {

    public static IPlatosDAO getPlatDAO() {

        IConexionBD conexion = new Conexion();
        return new PlatosDAO(conexion);

    }

    public static IUsuarioDAO getUsuarioDAO() {

        IConexionBD conexion = new Conexion(); // Aquí inicializa tu conexión como sea necesario
        return new UsuarioDAO(conexion);
    }

    public static IPedidosDAO getPediDAO() {

        IConexionBD conexion = new Conexion();
        return new PedidosDAO(conexion);

    }

    public static ISalasDAO getSalDAO() {

        IConexionBD conexion = new Conexion();
        return new SalasDAO(conexion);
    }

}
