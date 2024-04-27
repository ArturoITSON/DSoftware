/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.LoginDAO;
import DTO.*;
import Interfaces.ILoginDAO;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class LoginControl {

    private ILoginDAO loginDAO;

    public LoginControl() {
        this.loginDAO = LoginDAO.getInstance();
    }

    public LoginDTO log(String usuario, String pass) throws PersistenciaException {
        return loginDAO.log(usuario, pass);
    }

    public boolean Registrar(LoginDTO reg) throws PersistenciaException {
        return loginDAO.Registrar(reg);
    }

    public List ListarUsuarios() throws PersistenciaException {
        return loginDAO.ListarUsuarios();
    }

    public boolean ModificarDatos(ConfigDTO conf) throws PersistenciaException {
        return loginDAO.ModificarDatos(conf);
    }

    public ConfigDTO datosEmpresa() throws PersistenciaException {
        return loginDAO.datosEmpresa();
    }

}
