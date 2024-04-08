/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.PlatosDTO;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface IPlatosDAO {

    public boolean Registrar(PlatosDTO pla) throws PersistenciaException;

    public List Listar(String valor, String fecha) throws PersistenciaException;

    public boolean Eliminar(int id) throws PersistenciaException;

    public boolean Modificar(PlatosDTO pla) throws PersistenciaException;

}
