/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.SalasDTO;
import EntidadesJPA.Sala;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface ISalasDAO {

    public boolean RegistrarSala(Sala sl) throws PersistenciaException;

    public List Listar() throws PersistenciaException;

    public boolean Eliminar(int id) throws PersistenciaException;

    public boolean Modificar(Sala sl) throws PersistenciaException;

    public Sala obtenerSalaPorId(int idSala) throws PersistenciaException;

    public Sala obtenerSalaPorNombre(String nombreSala) throws PersistenciaException;

}
