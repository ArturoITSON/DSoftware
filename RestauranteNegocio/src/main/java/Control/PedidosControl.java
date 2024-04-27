/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;


import DAO.PedidosDAO;
import DTO.*;
import Interfaces.IPedidosDAO;
import Persistencia.PersistenciaException;
import com.itextpdf.text.DocumentException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class PedidosControl {
    
    private final IPedidosDAO pedidosDAO;

    public PedidosControl() {
        this.pedidosDAO = PedidosDAO.getInstance();
    }

    public int obtenerIdPedido() throws PersistenciaException {
        return pedidosDAO.IdPedido();
    }

    public int verificarEstadoPedido(int mesa, int id_sala) throws PersistenciaException {
        return pedidosDAO.verificarEstado(mesa, id_sala);
    }

    public int registrarPedido(PedidosDTO ped) throws PersistenciaException {
        return pedidosDAO.RegistrarPedido(ped);
    }

    public int registrarDetallePedido(DetallePedidoDTO det) throws PersistenciaException {
        return pedidosDAO.RegistrarDetalle(det);
    }

    public List verPedidoDetalle(int id_pedido) throws PersistenciaException {
        return pedidosDAO.verPedidoDetalle(id_pedido);
    }

    public PedidosDTO verPedido(int id_pedido) throws PersistenciaException {
        return pedidosDAO.verPedido(id_pedido);
    }

    public List finalizarPedido(int id_pedido) throws PersistenciaException {
        return pedidosDAO.finalizarPedido(id_pedido);
    }

    public void generarPDFPedido(int id_pedido) throws PersistenciaException, DocumentException {
        pedidosDAO.pdfPedido(id_pedido);
    }

    public boolean actualizarEstadoPedido(int id_pedido) throws PersistenciaException {
        return pedidosDAO.actualizarEstado(id_pedido);
    }

    public List listarPedidos() throws PersistenciaException {
        return pedidosDAO.listarPedidos();
    }
    
    
}
