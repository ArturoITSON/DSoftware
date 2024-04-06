/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author Ruben
 */
//import Conexion.ConexionBD;
//import DTOs.PedidoDTO;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class PedidoDAO {
//    public void crearPedido(PedidoDTO pedido) {
//        try (Connection conn = ConexionBD.obtenerConexion();
//             PreparedStatement stmt = conn.prepareStatement("INSERT INTO pedido (fechaHora, estado) VALUES (?, ?)")) {
//            stmt.setTimestamp(1, new java.sql.Timestamp(pedido.getFechaHora().getTime()));
//            stmt.setString(2, pedido.getEstado());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public PedidoDTO obtenerPedido(int id) {
//        PedidoDTO pedido = null;
//        try (Connection conn = ConexionBD.obtenerConexion();
//             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pedido WHERE id = ?")) {
//            stmt.setInt(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    pedido = new PedidoDTO();
//                    pedido.setId(rs.getInt("id"));
//                    pedido.setFechaHora(rs.getTimestamp("fechaHora"));
//                    pedido.setEstado(rs.getString("estado"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return pedido;
//    }
//
//    public void modificarPedido(PedidoDTO pedido) {
//        try (Connection conn = ConexionBD.obtenerConexion();
//             PreparedStatement stmt = conn.prepareStatement("UPDATE pedido SET fechaHora = ?, estado = ? WHERE id = ?")) {
//            stmt.setTimestamp(1, new java.sql.Timestamp(pedido.getFechaHora().getTime()));
//            stmt.setString(2, pedido.getEstado());
//            stmt.setInt(3, pedido.getId());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}

