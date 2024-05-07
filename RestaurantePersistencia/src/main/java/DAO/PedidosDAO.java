/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.Conexion;
import Conexion.IConexionBD;
import DTO.DetallePedidoDTO;
import DTO.PedidosDTO;
import EntidadesJPA.DetallePedido;
import EntidadesJPA.Pedido;
import Interfaces.IPedidosDAO;
import Persistencia.PersistenciaException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Carlo
 */
public class PedidosDAO implements IPedidosDAO {

    private IConexionBD conexion;
    private static PedidosDAO instance;

    // Constructor público que recibe la conexión como parámetro
    public PedidosDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    // Método para obtener una instancia única de LoginDAO
    public static PedidosDAO getInstance(IConexionBD conexion) {
        if (instance == null) {
            instance = new PedidosDAO(conexion);
        }
        return instance;
    }

    @Override
    public int IdPedido() {
        int id = 0;
        String jpql = "SELECT MAX(p.id) FROM Pedido p";
        try {
            // Obtenemos el EntityManager
            EntityManager entityManager = conexion.conexion();

            // Creación de la consulta con TypedQuery
            TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);

            // Obtención del resultado
            id = query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return id;
    }

    @Override
    public int verificarEstado(int mesa, int id_sala) {
        int id_pedido = 0;
        String jpql = "SELECT p.id FROM Pedido p WHERE p.numMesa = :mesa AND p.sala.id = :id_sala AND p.estado = :estado";
        try {
            // Obtenemos el EntityManager
            EntityManager entityManager = conexion.conexion();

            // Creación de la consulta con TypedQuery
            TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);

            // Asignación de parámetros
            query.setParameter("mesa", mesa);
            query.setParameter("id_sala", id_sala);
            query.setParameter("estado", "PENDIENTE");

            // Obtención del resultado
            id_pedido = query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return id_pedido;
    }

    @Override
    public int RegistrarPedido(Pedido ped) throws PersistenciaException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        int idGenerado = 0;

        try {
            entityManager = conexion.conexion();
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(ped);
            entityManager.flush();
            idGenerado = (int) ped.getId(); // El ID se generará automáticamente por la base de datos

            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al registrar el pedido", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return idGenerado;
    }

    @Override
    public int RegistrarDetalle(DetallePedido det) throws PersistenciaException {
        int idGenerado = 0; // Cambiar el tipo de dato a int

        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = conexion.conexion();
            transaction = entityManager.getTransaction();
            transaction.begin();

            Pedido pedido = entityManager.find(Pedido.class, det.getId());
            if (pedido != null) {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setNombre(det.getNombre());
                detallePedido.setPrecio(det.getPrecio());
                detallePedido.setCantidad(det.getCantidad());
                detallePedido.setComentario(det.getComentario());
                detallePedido.setPedido(pedido);

                entityManager.persist(detallePedido);
                entityManager.flush();
                idGenerado = (int) detallePedido.getId(); // El ID se generará automáticamente por la base de datos

                transaction.commit();
            } else {
                throw new PersistenciaException("No se encontró el pedido con el ID proporcionado.");
            }
        } catch (PersistenceException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al registrar el detalle del pedido", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return idGenerado;
    }

    @Override
    public List verPedidoDetalle(int id_pedido) throws PersistenciaException {
        List<DetallePedidoDTO> listaDetalles = new ArrayList<>();
        try {
            EntityManager entityManager = conexion.conexion();
            String jpql = "SELECT d FROM DetallePedido d WHERE d.pedido.id = :id_pedido";
            TypedQuery<DetallePedido> query = entityManager.createQuery(jpql, DetallePedido.class);
            query.setParameter("id_pedido", id_pedido);
            List<DetallePedido> detalles = query.getResultList();
            for (DetallePedido detalle : detalles) {
                DetallePedidoDTO det = new DetallePedidoDTO();
                det.setId((int) detalle.getId());
                det.setNombre(detalle.getNombre());
                det.setPrecio(detalle.getPrecio());
                det.setCantidad(detalle.getCantidad());
                det.setComentario(detalle.getComentario());
                listaDetalles.add(det);
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error al listar los detalles del pedido", e);
        }
        return listaDetalles;

    }

    @Override
    public Pedido verPedido(int idPedido) throws PersistenciaException {
        Pedido pedido;
        try {
            EntityManager entityManager = conexion.conexion();
            String jpql = "SELECT p FROM Pedido p JOIN FETCH p.sala s WHERE p.id = :idPedido";
            TypedQuery<Pedido> query = entityManager.createQuery(jpql, Pedido.class);
            query.setParameter("idPedido", idPedido);
            pedido = query.getSingleResult();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener el pedido", e);
        }
        return pedido;
    }

    @Override
    public List finalizarPedido(int idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        try {
            // Obtenemos el EntityManager
            EntityManager entityManager = conexion.conexion();

            // Consulta JPQL para obtener los detalles del pedido específico
            String jpql = "SELECT d FROM DetallePedido d WHERE d.pedido.id = :idPedido";

            // Creamos la TypedQuery con la consulta JPQL
            TypedQuery<DetallePedido> query = entityManager.createQuery(jpql, DetallePedido.class);

            // Asignamos el parámetro
            query.setParameter("idPedido", idPedido);

            // Obtenemos el resultado
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Errorcito" + e);
        }
        return lista;
    }

    @Override
    public void pdfPedido(int idPedido) throws DocumentException {
        try {
            // Obtener el EntityManager
            EntityManager entityManager = conexion.conexion();

            // Consulta JPQL para obtener la información del pedido
            String jpql = "SELECT p, s.nombre FROM Pedido p JOIN p.sala s WHERE p.id = :idPedido";

            // Crear la TypedQuery con la consulta JPQL
            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);

            // Asignar el parámetro
            query.setParameter("idPedido", idPedido);

            // Obtener el resultado
            Object[] result = query.getSingleResult();

            // Extraer la información del resultado
            Pedido pedido = (Pedido) result[0];
            String nombreSala = (String) result[1];
            String fechaPedido = pedido.getFecha().toString();
            String usuario = pedido.getUsuario();
            String total = String.valueOf(pedido.getTotal());

            // Crear el archivo PDF
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + File.separator + "pedido.pdf");
            FileOutputStream archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            // Agregar información del restaurante
            agregarInformacionRestaurante(doc);

            // Agregar espacio antes de la tabla
            doc.add(Chunk.NEWLINE);

            // Agregar contenido al PDF
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            tabla.addCell("Fecha: " + fechaPedido);
            tabla.addCell("Atendido por: " + usuario);
            tabla.addCell("Sala: " + nombreSala);
            tabla.addCell("Total: " + total);
            

            // Agregar la tabla al documento
            doc.add(tabla);
            

            // Cerrar el documento y abrir el archivo generado
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException e) {
            System.out.println("Pedido no encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al generar el PDF: " + e.getMessage());
        }
    }

    private void agregarInformacionRestaurante(Document document) throws DocumentException {
        // Agregar información del restaurante
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        // Obtener la información del restaurante desde la base de datos
        // Aquí debes obtener la información de la entidad Config y asignarla a las variables correspondientes
        String ruc = "123456789";
        String nombreRestaurante = "Restaurante CHALET";
        String telefono = "1234567890";
        String direccion = "Cd-Obregon";

        // Crear título del restaurante
        Paragraph tituloRestaurante = new Paragraph(nombreRestaurante, fontTitulo);
        tituloRestaurante.setAlignment(Element.ALIGN_CENTER);
        document.add(tituloRestaurante);

        // Agregar espacio después del título
        document.add(Chunk.NEWLINE);

        // Crear tabla para la información del restaurante
        PdfPTable tablaRestaurante = new PdfPTable(2);
        tablaRestaurante.setWidthPercentage(100);

        // Agregar filas con la información del restaurante
        tablaRestaurante.addCell(new Phrase("RUC:", fontNormal));
        tablaRestaurante.addCell(new Phrase(ruc, fontNormal));
        tablaRestaurante.addCell(new Phrase("Teléfono:", fontNormal));
        tablaRestaurante.addCell(new Phrase(telefono, fontNormal));
        tablaRestaurante.addCell(new Phrase("Dirección:", fontNormal));
        tablaRestaurante.addCell(new Phrase(direccion, fontNormal));

        // Agregar la tabla al documento
        document.add(tablaRestaurante);

        // Agregar espacio después del mensaje
        document.add(Chunk.NEWLINE);
    }
    
    
    

    @Override
    public boolean actualizarEstado(int idPedido) throws PersistenciaException {

        /// Obtener el EntityManager
        EntityManager entityManager = conexion.conexion();

        // Iniciar una transacción
        EntityTransaction transaction = entityManager.getTransaction();
        try {

            transaction.begin();

            // Obtener el pedido por su ID
            Pedido pedido = entityManager.find(Pedido.class, idPedido);

            // Verificar si se encontró el pedido
            if (pedido == null) {
                System.out.println("Pedido no encontrado.");
                return false;
            }

            // Actualizar el estado del pedido a "FINALIZADO"
            pedido.setEstado("FINALIZADO");

            // Hacer commit de la transacción
            transaction.commit();

            return true;
        } catch (Exception e) {
            // En caso de error, hacer rollback de la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al actualizar el estado del pedido: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List listarPedidos() throws PersistenciaException {

        // Obtener el EntityManager
        EntityManager entityManager = conexion.conexion();
        try {

            // Construir la consulta JPQL
            String jpql = "SELECT p FROM Pedido p JOIN FETCH p.sala ORDER BY p.fecha DESC";

            // Crear la consulta con TypedQuery
            TypedQuery<Pedido> query = entityManager.createQuery(jpql, Pedido.class);

            // Obtener la lista de pedidos
            List<Pedido> pedidos = query.getResultList();

            return pedidos;
        } catch (Exception e) {
            System.out.println("Error al listar los pedidos: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}
