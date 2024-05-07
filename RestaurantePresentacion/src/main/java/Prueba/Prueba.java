/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Prueba;

import Control.Convertidor;
import Control.Factory;
import GUI.frmLogin;
import Interfaces.IUsuarioDAO;

/**
 *
 * @author Carlo
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        IUsuarioDAO loginControl = Factory.getUsuarioDAO();
        Convertidor convertir = new Convertidor(); // Asume que el constructor no requiere argumentos
        new frmLogin(loginControl, convertir).setVisible(true);

    }

}
