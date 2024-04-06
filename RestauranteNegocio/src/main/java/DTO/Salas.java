/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Carlo
 */
public class Salas {
    
     private int id;
    private String nombre;
    private int mesas;
    
    public Salas(){
        
    }

    public Salas(int id, String nombre, int mesas) {
        this.id = id;
        this.nombre = nombre;
        this.mesas = mesas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMesas() {
        return mesas;
    }

    public void setMesas(int mesas) {
        this.mesas = mesas;
    }
    
    
}
