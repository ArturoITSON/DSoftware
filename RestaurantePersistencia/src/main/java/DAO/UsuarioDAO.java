package DAO;

import Conexion.EntityManagerSingleton;
import Conexion.IConexionBD;

import EntidadesJPA.Config;
import EntidadesJPA.Usuario;
import Interfaces.IUsuarioDAO;
import Persistencia.PersistenciaException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Carlo
 */
public class UsuarioDAO implements IUsuarioDAO {

    private IConexionBD conexion;
    private static UsuarioDAO instance;

    // Constructor público que recibe la conexión como parámetro
    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    // Método para obtener una instancia única de UsuarioDAO
    public static UsuarioDAO getInstance(IConexionBD conexion) {
        if (instance == null) {
            instance = new UsuarioDAO(conexion);
        }
        return instance;
    }

    @Override
    public Usuario log(String usuario, String pass) throws PersistenciaException {

        EntityManager entityManager = null;
        entityManager = EntityManagerSingleton.obtenerEntityManager();
        Usuario l = new Usuario();
        // Construcción de la consulta JPQL
        String jpql = "SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.pass = :pass";
        // Creación de la consulta con TypedQuery
        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        // Asignación de parámetros
        query.setParameter("usuario", usuario);
        query.setParameter("pass", pass);
        // Obtención del resultado
        Usuario usuarioEncontrado = query.getSingleResult();
        // Asignación de los valorfes al DTO
        l.setId((int) usuarioEncontrado.getId());
        l.setNombre(usuarioEncontrado.getNombre());
        l.setUsuario(usuarioEncontrado.getUsuario());
        l.setPass(usuarioEncontrado.getPass());
        l.setRol(usuarioEncontrado.getRol());
        return l;
    }

    @Override
    public void Registrar(Usuario reg) throws PersistenciaException {
        EntityManager entityManager = null;
        entityManager = EntityManagerSingleton.obtenerEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Comenzar la transacción
            transaction.begin();

            // Crear un nuevo objeto Usuario y establecer sus atributos
            Usuario usuario = new Usuario();
            usuario.setNombre(reg.getNombre());
            usuario.setUsuario(reg.getUsuario());
            usuario.setPass(reg.getPass());
            usuario.setRol(reg.getRol());

            // Persistir el objeto Usuario en la base de datos
            entityManager.persist(usuario);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            // Revertir la transacción en caso de error
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Lanzar una excepción para notificar sobre el error
            throw new PersistenciaException("Error al intentar registrar usuario", e);
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List ListarUsuarios() throws PersistenciaException {

        EntityManager entityManager = null;
        entityManager = EntityManagerSingleton.obtenerEntityManager();
        List<Usuario> lista = null;
        try {
            TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class);
            lista = query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al listar usuarios", e);
        }
        return lista;

    }

    @Override
    public void ModificarDatos(Config conf) throws PersistenciaException {
        EntityManager entityManager = null;
        entityManager = EntityManagerSingleton.obtenerEntityManager();
        try {
            Config configuracion = entityManager.find(Config.class, conf.getId());
            if (configuracion != null) {
                entityManager.getTransaction().begin();
                configuracion.setRuc(conf.getRuc());
                configuracion.setNombre(conf.getNombre());
                configuracion.setTelefono(conf.getTelefono());
                configuracion.setDireccion(conf.getDireccion());
                configuracion.setMensaje(conf.getMensaje());
                entityManager.getTransaction().commit();
            } else {
                throw new PersistenciaException("No se encontró la configuración en la base de datos.");
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error al modificar la configuración: " + e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Config datosEmpresa() throws PersistenciaException {

        EntityManager entityManager = null;
        entityManager = EntityManagerSingleton.obtenerEntityManager();

        List<Config> configuraciones = entityManager.createQuery("SELECT c FROM Config c", Config.class).getResultList();
        if (!configuraciones.isEmpty()) {
            return configuraciones.get(0); // Suponiendo que solo hay una configuración en la base de datos
        } else {
            return null;
        }

    }

}
