/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.ConfigDTO;
import DTO.LoginDTO;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface ILoginDAO {
    
    public LoginDTO log(String usuario, String pass)throws PersistenciaException;
    
    public boolean Registrar(LoginDTO reg)throws PersistenciaException;
    
    public List ListarUsuarios()throws PersistenciaException;
    
    public boolean ModificarDatos(ConfigDTO conf)throws PersistenciaException;
            
    public ConfigDTO datosEmpresa()throws PersistenciaException;
    
    
    
    
    
}
