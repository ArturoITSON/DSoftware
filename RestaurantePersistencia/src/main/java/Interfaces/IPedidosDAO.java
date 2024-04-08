/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.DetallePedidoDTO;
import DTO.PedidosDTO;
import Persistencia.PersistenciaException;
import com.itextpdf.text.DocumentException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface IPedidosDAO {

    public int IdPedido() throws PersistenciaException;

    public int verificarEstado(int mesa, int id_sala) throws PersistenciaException;

    public int RegistrarPedido(PedidosDTO ped) throws PersistenciaException;

    public int RegistrarDetalle(DetallePedidoDTO det) throws PersistenciaException;

    public List verPedidoDetalle(int id_pedido) throws PersistenciaException;

    public PedidosDTO verPedido(int id_pedido) throws PersistenciaException;

    public List finalizarPedido(int id_pedido) throws PersistenciaException;

    public void pdfPedido(int id_pedido) throws DocumentException;

    public boolean actualizarEstado(int id_pedido) throws PersistenciaException;

    public List listarPedidos() throws PersistenciaException;

}
