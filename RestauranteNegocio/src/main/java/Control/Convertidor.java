/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Conexion.IConexionBD;
import DAO.SalasDAO;
import DTO.ConfigDTO;
import DTO.DetallePedidoDTO;
import DTO.PedidosDTO;
import DTO.PlatosDTO;
import DTO.SalasDTO;
import DTO.UsuarioDTO;
import EntidadesJPA.Config;
import EntidadesJPA.DetallePedido;
import EntidadesJPA.Pedido;
import EntidadesJPA.Plato;
import EntidadesJPA.Sala;

import EntidadesJPA.Usuario;
import Interfaces.ISalasDAO;
import Persistencia.PersistenciaException;
import java.sql.Timestamp;

/**
 *
 * @author Carlo
 */
public class Convertidor {

    ISalasDAO salita = Factory.getSalDAO();

    public Usuario convertirUsuarioDTO(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setPass(usuarioDTO.getPass());
        usuario.setRol(usuarioDTO.getRol());
        return usuario;
    }

    public UsuarioDTO convertirUsuarioAUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId((int) usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setUsuario(usuario.getUsuario());
        usuarioDTO.setRol(usuario.getRol());
        // Agrega más asignaciones según sea necesario para otros atributos
        return usuarioDTO;
    }

    public static Plato convertirPlatoDTOaEntidad(PlatosDTO platoDTO) {
        Plato plato = new Plato();
        plato.setNombre(platoDTO.getNombre());
        plato.setPrecio(platoDTO.getPrecio());
        // Asigna otros atributos según sea necesario
        return plato;
    }

    public static Sala convertirSalaDTOaEntidad(SalasDTO salaDTO) {
        Sala sala = new Sala();
        sala.setNombre(salaDTO.getNombre());
        sala.setMesas(salaDTO.getMesas());
        // Asigna otros atributos según sea necesario
        return sala;
    }

    public static Config convertirConfigDTO(ConfigDTO configDTO) {
        Config config = new Config();
        config.setId(configDTO.getId());
        config.setRuc(configDTO.getRuc());
        config.setNombre(configDTO.getNombre());
        config.setTelefono(configDTO.getTelefono());
        config.setDireccion(configDTO.getDireccion());
        config.setMensaje(configDTO.getMensaje());
        return config;
    }

    public Pedido convertirPedidoDTOaEntidad(PedidosDTO pedidoDTO) throws PersistenciaException {
        try {
            // Obtener los valores del objeto DTO
            int idSala = pedidoDTO.getId_sala();
            int numMesa = pedidoDTO.getNum_mesa();
            double total = pedidoDTO.getTotal();
            String usuario = pedidoDTO.getUsuario();

            // Crear una nueva instancia de Pedido con los valores obtenidos
            Timestamp fecha = new Timestamp(System.currentTimeMillis()); // Puedes establecer la fecha actual o pasarla como parámetro si está disponible
            String estado = "PENDIENTE"; // Puedes establecer el estado inicial como "PENDIENTE"

            // Crear una instancia de Pedido sin especificar la sala
            Pedido pedido = new Pedido(null, numMesa, fecha, total, estado, usuario);

            // Asignar la sala al pedido
            Sala sala = new Sala();
            sala.setId(idSala);
            pedido.setSala(sala);

            // Devolver el objeto Pedido creado
            return pedido;
        } catch (Exception e) {
            throw new PersistenciaException("Error al convertir el DTO a entidad Pedido", e);
        }
    }

    public DetallePedido convertirDetallePedidoDTOaEntidad(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setNombre(detallePedidoDTO.getNombre());
        detallePedido.setCantidad(detallePedidoDTO.getCantidad());
        detallePedido.setPrecio(detallePedidoDTO.getPrecio());
        detallePedido.setComentario(detallePedidoDTO.getComentario());
        detallePedido.setId(detallePedidoDTO.getId_pedido());
        return detallePedido;
    }

    public PedidosDTO convertirPedidoEntidadADTO(EntidadesJPA.Pedido pedidoEntidad) {
        PedidosDTO pedidoDTO = new PedidosDTO();
        pedidoDTO.setId((int) pedidoEntidad.getId());
        pedidoDTO.setSala(pedidoEntidad.getSala().getNombre());
        pedidoDTO.setUsuario(pedidoEntidad.getUsuario());
        pedidoDTO.setNum_mesa(pedidoEntidad.getNumMesa());
        pedidoDTO.setFecha(pedidoEntidad.getFecha());
        pedidoDTO.setTotal(pedidoEntidad.getTotal());
        pedidoDTO.setEstado(pedidoEntidad.getEstado());
        return pedidoDTO;
    }
    
    public UsuarioDTO convertirUsuarioEntidadADTO(EntidadesJPA.Usuario usuarioEntidad) {
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    usuarioDTO.setId((int) usuarioEntidad.getId());
    usuarioDTO.setNombre(usuarioEntidad.getNombre());
    usuarioDTO.setUsuario(usuarioEntidad.getUsuario());
    usuarioDTO.setRol(usuarioEntidad.getRol());
    // Aquí puedes agregar más conversiones si es necesario
    return usuarioDTO;
}

}
