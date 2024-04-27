/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.Conexion;
import DTO.DetallePedidoDTO;
import DTO.PedidosDTO;
import Interfaces.IPedidosDAO;
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
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Carlo
 */
public class PedidosDAO implements IPedidosDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Conexion cn = Conexion.obtenerInstancia();
    int r;
    
    
    private static PedidosDAO instance;

    // Constructor privado para evitar la instanciación directa
    private PedidosDAO() {}

    // Método estático para obtener la instancia única de PlatosDAO
    public static PedidosDAO getInstance() {
        if (instance == null) {
            instance = new PedidosDAO();
        }
        return instance;
    }
    
    
    

    @Override
    public int IdPedido() {
        int id = 0;
        String sql = "SELECT MAX(id) FROM pedidos";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }

    @Override
    public int verificarEstado(int mesa, int id_sala) {
        int id_pedido = 0;
        String sql = "SELECT id FROM pedidos WHERE num_mesa=? AND id_sala=? AND estado = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, mesa);
            ps.setInt(2, id_sala);
            ps.setString(3, "PENDIENTE");
            rs = ps.executeQuery();
            if (rs.next()) {
                id_pedido = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id_pedido;
    }

    @Override
    public int RegistrarPedido(PedidosDTO ped) {
        String sql = "INSERT INTO pedidos (id_sala, num_mesa, total, usuario) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ped.getId_sala());
            ps.setInt(2, ped.getNum_mesa());
            ps.setDouble(3, ped.getTotal());
            ps.setString(4, ped.getUsuario());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }

    @Override
    public int RegistrarDetalle(DetallePedidoDTO det) {
        String sql = "INSERT INTO detalle_pedidos (nombre, precio, cantidad, comentario, id_pedido) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, det.getNombre());
            ps.setDouble(2, det.getPrecio());
            ps.setInt(3, det.getCantidad());
            ps.setString(4, det.getComentario());
            ps.setInt(5, det.getId_pedido());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    @Override
    public List verPedidoDetalle(int id_pedido) {
        List<DetallePedidoDTO> Lista = new ArrayList();
        String sql = "SELECT d.* FROM pedidos p INNER JOIN detalle_pedidos d ON p.id = d.id_pedido WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_pedido);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetallePedidoDTO det = new DetallePedidoDTO();
                det.setId(rs.getInt("id"));
                det.setNombre(rs.getString("nombre"));
                det.setPrecio(rs.getDouble("precio"));
                det.setCantidad(rs.getInt("cantidad"));
                det.setComentario(rs.getString("comentario"));
                Lista.add(det);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    @Override
    public PedidosDTO verPedido(int id_pedido) {
        PedidosDTO ped = new PedidosDTO();
        String sql = "SELECT p.*, s.nombre FROM pedidos p INNER JOIN salas s ON p.id_sala = s.id WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_pedido);
            rs = ps.executeQuery();
            if (rs.next()) {

                ped.setId(rs.getInt("id"));
                ped.setFecha(rs.getString("fecha"));
                ped.setSala(rs.getString("nombre"));
                ped.setNum_mesa(rs.getInt("num_mesa"));
                ped.setTotal(rs.getDouble("total"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ped;
    }

    @Override
    public List finalizarPedido(int id_pedido) {
        List<DetallePedidoDTO> Lista = new ArrayList();
        String sql = "SELECT d.* FROM pedidos p INNER JOIN detalle_pedidos d ON p.id = d.id_pedido WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_pedido);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetallePedidoDTO det = new DetallePedidoDTO();
                det.setId(rs.getInt("id"));
                det.setNombre(rs.getString("nombre"));
                det.setPrecio(rs.getDouble("precio"));
                det.setCantidad(rs.getInt("cantidad"));
                det.setComentario(rs.getString("comentario"));
                Lista.add(det);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    @Override
    public void pdfPedido(int id_pedido) throws DocumentException {
       Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String fechaPedido = null, usuario = null, total = null, sala = null, num_mesa = null;
        
        try {
            // Establecer conexión y crear el archivo PDF
            con = cn.getConnection();
            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + File.separator + "pedido.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            
            // Obtener información del pedido
            String informacion = "SELECT p.*, s.nombre FROM pedidos p INNER JOIN salas s ON p.id_sala = s.id WHERE p.id = ?";
            try {
                ps = con.prepareStatement(informacion);
                ps.setInt(1, id_pedido);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num_mesa = rs.getString("num_mesa");
                    sala = rs.getString("nombre");
                    fechaPedido = rs.getString("fecha");
                    usuario = rs.getString("usuario");
                    total = rs.getString("total");
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener información del pedido: " + e.toString());
            }
            
            // Crear tabla para el encabezado
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{20f, 20f, 60f, 60f};
            Encabezado.setWidths(columnWidthsEncabezado);
            
            
            // Agregar contenido al encabezado
            Encabezado.addCell(""); // Celda vacía
            // Agregar información de la empresa
            String config = "SELECT * FROM config";
            String mensaje = "";
            try {
                con = cn.getConnection();
                ps = con.prepareStatement(config);
                rs = ps.executeQuery();
                if (rs.next()) {
                    mensaje = rs.getString("mensaje");
                    Encabezado.addCell("Ruc:    " + rs.getString("ruc") 
                            + "\nNombre: " + rs.getString("nombre") 
                            + "\nTeléfono: " + rs.getString("telefono") 
                            + "\nDirección: " + rs.getString("direccion")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Paragraph info = new Paragraph();
            
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            info.add("Atendido: " + usuario 
                    + "\nN° Pedido: " + id_pedido + "                                                                          " + "Ruc:    " + rs.getString("ruc") 
                    + "\nFecha: " + fechaPedido   + "                                                   " + "Nombre: " + rs.getString("nombre")
                    + "\nSala: " + sala           + "                                                                         " + "Teléfono: " + rs.getString("telefono") 
                    + "\nN° Mesa: " + num_mesa    + "                                                                              " + "Dirección: " + rs.getString("direccion")
                    + "\n "
                    + "\n "
                    
                             
                            
                            
            );
            Encabezado.setHorizontalAlignment(Element.ALIGN_RIGHT);
            Encabezado.addCell(info);
            
            // Añadir tablas y párrafos al documento
            try {
                doc.add(Encabezado);
                doc.add(info);
                // Añadir tabla de productos
                PdfPTable tabla = new PdfPTable(4);
                tabla.setWidthPercentage(100);
                tabla.getDefaultCell().setBorder(0);
                float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
                tabla.setWidths(columnWidths);
                tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
                // Celdas con texto en negrita
                PdfPCell c1 = new PdfPCell(new Phrase("Cant.", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
                PdfPCell c2 = new PdfPCell(new Phrase("Plato.", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
                PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
                PdfPCell c4 = new PdfPCell(new Phrase("P. Total", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
                // Configurar celdas
                c1.setBorder(Rectangle.NO_BORDER);
                c2.setBorder(Rectangle.NO_BORDER);
                c3.setBorder(Rectangle.NO_BORDER);
                c4.setBorder(Rectangle.NO_BORDER);
                c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tabla.addCell(c1);
                tabla.addCell(c2);
                tabla.addCell(c3);
                tabla.addCell(c4);
                // Consultar productos del pedido
                String productQuery = "SELECT d.* FROM pedidos p INNER JOIN detalle_pedidos d ON p.id = d.id_pedido WHERE p.id = ?";
                ps = con.prepareStatement(productQuery);
                ps.setInt(1, id_pedido);
                rs = ps.executeQuery();
                while (rs.next()) {
                    double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                    tabla.addCell(rs.getString("cantidad"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("precio"));
                    tabla.addCell(String.valueOf(subTotal));
                }
                doc.add(tabla); // Agregar tabla de productos al documento
                
                // Párrafo con el total
                Paragraph totalParrafo = new Paragraph();
                totalParrafo.add(Chunk.NEWLINE);
                totalParrafo.add("Total S/: " + total);
                totalParrafo.setAlignment(Element.ALIGN_RIGHT);
                doc.add(totalParrafo);
                
                // Párrafo para la firma y mensaje
                Paragraph firma = new Paragraph();
                firma.add(Chunk.NEWLINE);
                firma.add("Cancelación \n\n");
                firma.add("------------------------------------\n");
                firma.add("Firma \n");
                firma.setAlignment(Element.ALIGN_CENTER);
                doc.add(firma);
                
                Paragraph mensajeParrafo = new Paragraph();
                mensajeParrafo.add(Chunk.NEWLINE);
                mensajeParrafo.add(mensaje);
                mensajeParrafo.setAlignment(Element.ALIGN_CENTER);
                doc.add(mensajeParrafo);
                
                // Cerrar el documento y abrir el archivo generado
                doc.close();
                archivo.close();
                Desktop.getDesktop().open(salida);
                
            } catch (DocumentException | IOException e) {
                System.out.println("Error al agregar contenido al PDF: " + e.toString());
            }
            
        } catch (FileNotFoundException | SQLException e) {
            System.out.println("Error al generar el PDF: " + e.toString());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.toString());
            }
        }
    }
    




    

    @Override
    public boolean actualizarEstado(int id_pedido) {
        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "FINALIZADO");
            ps.setInt(2, id_pedido);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public List listarPedidos() {
        List<PedidosDTO> Lista = new ArrayList();
        String sql = "SELECT p.*, s.nombre FROM pedidos p INNER JOIN salas s ON p.id_sala = s.id ORDER BY p.fecha DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PedidosDTO ped = new PedidosDTO();
                ped.setId(rs.getInt("id"));
                ped.setSala(rs.getString("nombre"));
                ped.setNum_mesa(rs.getInt("num_mesa"));
                ped.setFecha(rs.getString("fecha"));
                ped.setTotal(rs.getDouble("total"));
                ped.setUsuario(rs.getString("usuario"));
                ped.setEstado(rs.getString("estado"));
                Lista.add(ped);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

}
