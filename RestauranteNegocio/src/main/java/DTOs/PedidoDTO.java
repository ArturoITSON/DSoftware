/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;

/**
 *
 * @author Ruben
 */
public class PedidoDTO {
    private int id;
    private Date fechaHora;
    private String estado;

    public PedidoDTO() {
    }

    public PedidoDTO(int id, Date fechaHora, String estado) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}