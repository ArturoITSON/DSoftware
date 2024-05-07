/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import DTO.PlatosDTO;
import EntidadesJPA.Plato;
import Interfaces.IPlatosDAO;
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
public class PlatosDAO implements IPlatosDAO {

    private IConexionBD conexion;
    private static PlatosDAO instance;

    // Constructor público que recibe la conexión como parámetro
    public PlatosDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    // Método para obtener una instancia única de LoginDAO
    public static PlatosDAO getInstance(IConexionBD conexion) {
        if (instance == null) {
            instance = new PlatosDAO(conexion);
        }
        return instance;
    }

    @Override
    public boolean Registrar(Plato pla) throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();
        EntityTransaction transaction = null;
        try {
            // Iniciar una transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Crear una instancia de la entidad Plato
            Plato plato = new Plato();
            plato.setNombre(pla.getNombre());
            plato.setPrecio(pla.getPrecio());
            plato.setFecha(pla.getFecha()); // Setear la fecha directamente

            // Guardar el plato en la base de datos
            entityManager.persist(plato);

            // Confirmar la transacción
            transaction.commit();

            return true;
        } catch (Exception e) {
            // Si ocurre un error, hacer rollback de la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al registrar el plato: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    @Override
    public List Listar(String valor, String fecha) throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();
        try {
            // Construir la consulta JPQL
            String jpql;
            if (valor.equalsIgnoreCase("")) {
                jpql = "SELECT p FROM Plato p";
            } else {
                jpql = "SELECT p FROM Plato p WHERE p.nombre LIKE :nombre";
            }

            // Crear la consulta con TypedQuery
            TypedQuery<Plato> query = entityManager.createQuery(jpql, Plato.class);
            if (!valor.equalsIgnoreCase("")) {
                query.setParameter("nombre", "%" + valor + "%");
            }

            // Obtener el resultado
            List<Plato> platos = query.getResultList();

            // Convertir los resultados a DTOs y retornar la lista
            List<PlatosDTO> listaDTO = new ArrayList<>();
            for (Plato plato : platos) {
                PlatosDTO platoDTO = new PlatosDTO();
                platoDTO.setId((int) plato.getId());
                platoDTO.setNombre(plato.getNombre());
                platoDTO.setPrecio(plato.getPrecio());
                platoDTO.setFecha(plato.getFecha());
                listaDTO.add(platoDTO);
            }
            return listaDTO;
        } catch (Exception e) {
            System.out.println("Error al listar los platos: " + e.getMessage());
            return new ArrayList<>();
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

            // Iniciar una transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Encontrar el Plato por su ID
            Plato plato = entityManager.find(Plato.class, id);
            if (plato != null) {
                // Si el plato existe, eliminarlo
                entityManager.remove(plato);
                transaction.commit();
                return true;
            } else {
                System.out.println("El plato con ID " + id + " no existe.");
                return false;
            }
        } catch (Exception e) {
            // Si ocurre algún error, realizar un rollback de la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al eliminar el plato: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public boolean Modificar(Plato pla) throws PersistenciaException {
        EntityManager entityManager = conexion.conexion();
        EntityTransaction transaction = null;
        try {
            // Obtener el EntityManager

            // Iniciar una transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Encontrar el Plato por su ID
            Plato plato = entityManager.find(Plato.class, pla.getId());
            if (plato != null) {
                // Si el plato existe, modificar sus atributos y guardar los cambios
                plato.setNombre(pla.getNombre());
                plato.setPrecio(pla.getPrecio());
                entityManager.merge(plato);
                transaction.commit();
                return true;
            } else {
                System.out.println("El plato con ID " + pla.getId() + " no existe.");
                return false;
            }
        } catch (Exception e) {
            // Si ocurre algún error, realizar un rollback de la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error al modificar el plato: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el EntityManager
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}
