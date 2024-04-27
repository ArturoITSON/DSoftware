/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.*;
import DTO.*;
import Interfaces.IPlatosDAO;
import Persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class PlatosControl {

    private final IPlatosDAO platosDAO;

    public PlatosControl() {
        this.platosDAO = PlatosDAO.getInstance();
    }

    public boolean Registrar(PlatosDTO pla) throws PersistenciaException {
        return platosDAO.Registrar(pla);
    }

    public List Listar(String valor, String fecha) throws PersistenciaException {
        return platosDAO.Listar(valor, fecha);
    }

    public boolean Eliminar(int id) throws PersistenciaException {
        return platosDAO.Eliminar(id);
    }

    public boolean Modificar(PlatosDTO pla) throws PersistenciaException {
        return platosDAO.Modificar(pla);
    }

}
