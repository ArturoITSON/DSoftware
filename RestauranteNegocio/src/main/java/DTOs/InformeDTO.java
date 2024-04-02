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
public class InformeDTO {
    private int id;
    private Date fechaGeneracion;
    private String tipoInforme;

    public InformeDTO() {
    }

    public InformeDTO(int id, Date fechaGeneracion, String tipoInforme) {
        this.id = id;
        this.fechaGeneracion = fechaGeneracion;
        this.tipoInforme = tipoInforme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getTipoInforme() {
        return tipoInforme;
    }

    public void setTipoInforme(String tipoInforme) {
        this.tipoInforme = tipoInforme;
    }

    
}
