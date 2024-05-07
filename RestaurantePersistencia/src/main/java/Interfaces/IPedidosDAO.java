/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;


import EntidadesJPA.DetallePedido;
import EntidadesJPA.Pedido;
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

    public int RegistrarPedido(Pedido ped) throws PersistenciaException;

    public int RegistrarDetalle(DetallePedido det) throws PersistenciaException;

    public List verPedidoDetalle(int id_pedido) throws PersistenciaException;

    public Pedido verPedido(int idPedido) throws PersistenciaException;

    public List finalizarPedido(int idPedido) throws PersistenciaException;

    public void pdfPedido(int idPedido) throws DocumentException;

    public boolean actualizarEstado(int idPedido) throws PersistenciaException;

    public List listarPedidos() throws PersistenciaException;

}
