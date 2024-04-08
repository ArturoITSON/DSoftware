/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.SalasDTO;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface ISalasDAO {

    public boolean RegistrarSala(SalasDTO sl) throws PersistenciaException;

    public List Listar() throws PersistenciaException;

    public boolean Eliminar(int id) throws PersistenciaException;

    public boolean Modificar(SalasDTO sl) throws PersistenciaException;

}
