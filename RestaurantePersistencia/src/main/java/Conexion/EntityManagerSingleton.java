/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ruben
 */
package Conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

    private static final String PERSISTENCE_UNIT_NAME = "CONEXIONPU";
    private static EntityManager entityManagerInstance;

    private EntityManagerSingleton() {
        // Constructor privado para evitar la creación de instancias
    }

    public static EntityManager obtenerEntityManager() {
        if (entityManagerInstance == null || !entityManagerInstance.isOpen()) {
            // Si no hay una instancia existente o está cerrada, crear una nueva instancia
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            entityManagerInstance = entityManagerFactory.createEntityManager();
        }
        return entityManagerInstance;
    }

    public static void cerrarEntityManager() {
        if (entityManagerInstance != null && entityManagerInstance.isOpen()) {
            // Si hay una instancia existente y está abierta, cerrarla
            entityManagerInstance.close();
        }
    }
}

