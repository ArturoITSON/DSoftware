/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import EntidadesJPA.Config;
import EntidadesJPA.Usuario;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface IUsuarioDAO {
    
    
    public Usuario log(String usuario, String pass)throws PersistenciaException;
    
    public void Registrar(Usuario reg)throws PersistenciaException;
    
    public List ListarUsuarios()throws PersistenciaException;
    
    public void ModificarDatos(Config conf)throws PersistenciaException;
            
    public Config datosEmpresa()throws PersistenciaException;
    
    
}
