/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.SalasDAO;
import DTO.SalasDTO;
import Interfaces.ISalasDAO;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class SalasControl {

    private final ISalasDAO salasDAO;

    public SalasControl() {
        this.salasDAO = SalasDAO.getInstance();
    }

    public boolean RegistrarSala(SalasDTO sl) throws PersistenciaException {
        return salasDAO.RegistrarSala(sl);
    }

    public List Listar() throws PersistenciaException {
        return salasDAO.Listar();
    }

    public boolean Eliminar(int id) throws PersistenciaException {
        return salasDAO.Eliminar(id);
    }

    public boolean Modificar(SalasDTO sl) throws PersistenciaException {
        return salasDAO.Modificar(sl);
    }

}
