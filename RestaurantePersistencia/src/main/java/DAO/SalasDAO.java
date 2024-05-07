/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import DTO.SalasDTO;

import EntidadesJPA.Sala;
import Interfaces.ISalasDAO;
import Persistencia.PersistenciaException;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Carlo
 */
public class SalasDAO implements ISalasDAO {

    private IConexionBD conexion;
    private static SalasDAO instance;

    public SalasDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    // Método para obtener una instancia única de LoginDAO
    public static SalasDAO getInstance(IConexionBD conexion) {
        if (instance == null) {
            instance = new SalasDAO(conexion);
        }
        return instance;
    }

    @Override
    public boolean RegistrarSala(Sala sl) throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();
        EntityTransaction transaction = null;
        try {
            // Obtener el EntityManager

            // Iniciar una transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Persistir la sala en la base de datos
            entityManager.persist(sl);

            // Confirmar la transacción
            transaction.commit();

            return true;
        } catch (Exception e) {
            // Si ocurre algún error, realizar un rollback de la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al registrar la sala: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List Listar() throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();
        try {
            // Obtener el EntityManager

            // Construir la consulta JPQL
            String jpql = "SELECT s FROM Sala s";

            // Crear la consulta con TypedQuery
            TypedQuery<Sala> query = entityManager.createQuery(jpql, Sala.class);

            // Obtener la lista de salas
            List<Sala> salas = query.getResultList();

            // Convertir las entidades Sala a DTO SalasDTO
            List<SalasDTO> listaSalasDTO = new ArrayList<>();
            for (Sala sala : salas) {
                SalasDTO sl = new SalasDTO();
                sl.setId((int) sala.getId());
                sl.setNombre(sala.getNombre());
                sl.setMesas(sala.getMesas());
                listaSalasDTO.add(sl);
            }

            return listaSalasDTO;
        } catch (Exception e) {
            System.out.println("Error al listar las salas: " + e.getMessage());
            return null;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public boolean Eliminar(int id) throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();
        EntityTransaction transaction = null;
        try {
            // Obtener el EntityManager

            // Iniciar la transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Encontrar la sala a eliminar
            Sala sala = entityManager.find(Sala.class, id);

            // Verificar si la sala existe
            if (sala != null) {
                // Eliminar la sala
                entityManager.remove(sala);

                // Confirmar la transacción
                transaction.commit();
                return true;
            } else {
                System.out.println("La sala con ID " + id + " no existe.");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                // Revertir la transacción si hay errores
                transaction.rollback();
            }
            System.out.println("Error al eliminar la sala: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public boolean Modificar(Sala sl) throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();
        EntityTransaction transaction = null;
        try {
            // Obtener el EntityManager

            // Iniciar la transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Encontrar la sala a modificar
            Sala sala = entityManager.find(Sala.class, sl.getId());

            // Verificar si la sala existe
            if (sala != null) {
                // Actualizar los atributos de la sala
                sala.setNombre(sl.getNombre());
                sala.setMesas(sl.getMesas());

                // Confirmar la transacción
                transaction.commit();
                return true;
            } else {
                System.out.println("La sala con ID " + sl.getId() + " no existe.");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                // Revertir la transacción si hay errores
                transaction.rollback();
            }
            System.out.println("Error al modificar la sala: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Sala obtenerSalaPorId(int idSala) throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();

        try {
            // Obtener el EntityManager

            // Buscar la Sala por su ID
            return entityManager.find(Sala.class, idSala);
        } catch (Exception e) {
            System.out.println("Error al obtener la Sala por ID: " + e.getMessage());
            return null;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Sala obtenerSalaPorNombre(String nombreSala) throws PersistenciaException {
EntityManager entityManager = null;
    try {
        // Obtener el EntityManager
        entityManager = conexion.conexion();

        // Consulta JPQL para obtener la sala por su nombre
        String jpql = "SELECT s FROM Sala s WHERE s.nombre = :nombre";

        // Crear la TypedQuery con la consulta JPQL
        TypedQuery<Sala> query = entityManager.createQuery(jpql, Sala.class);

        // Asignar el parámetro
        query.setParameter("nombre", nombreSala);

        // Ejecutar la consulta y obtener el resultado
        List<Sala> resultados = query.getResultList();

        // Verificar si se encontraron resultados
        if (!resultados.isEmpty()) {
            // Devolver la primera sala encontrada (suponiendo que no debería haber duplicados)
            return resultados.get(0);
        } else {
            // Si no se encontraron salas con ese nombre, devolver null
            return null;
        }
    } catch (Exception e) {
        // Manejar cualquier excepción y registrarla si es necesario
        throw new PersistenciaException("Error al obtener la sala por nombre: " + e.getMessage(), e);
    } finally {
        // Cerrar el EntityManager
        if (entityManager != null) {
            entityManager.close();
        }
    }    }
    
    

}
