package Conexion;

import Persistencia.PersistenciaException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Carlo
 */
public class Conexion implements IConexionBD {

    public Conexion() {
    }

    /**
     * Establece una conexión con la base de datos.
     *
     * @return Una instancia de EntityManager para interactuar con la base de
     * datos.
     *
     * @throws PersistenciaException Si ocurre un error durante la conexión.
     */
    @Override
    public EntityManager conexion() throws PersistenciaException {
        EntityManagerFactory entity = Persistence.createEntityManagerFactory("CONEXIONPU");
        EntityManager entityManager = entity.createEntityManager();
        return entityManager;
    }

}
