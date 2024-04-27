/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Prueba;

import Control.LoginControl;
import GUI.frmLogin;


/**
 *
 * @author Carlo
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LoginControl loginControl = new LoginControl(); // Ajusta según sea necesario

        new frmLogin(loginControl).setVisible(true);

    }

}
