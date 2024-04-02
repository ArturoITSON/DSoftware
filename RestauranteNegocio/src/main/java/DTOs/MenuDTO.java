/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;
import java.util.List;
/**
 *
 * @author Ruben
 */
public class MenuDTO {
    private int id;
    private Date fecha;
    private List<PlatilloDTO> platillosDestacados;

    public MenuDTO() {
    }

    public MenuDTO(int id, Date fecha, List<PlatilloDTO> platillosDestacados) {
        this.id = id;
        this.fecha = fecha;
        this.platillosDestacados = platillosDestacados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<PlatilloDTO> getPlatillosDestacados() {
        return platillosDestacados;
    }

    public void setPlatillosDestacados(List<PlatilloDTO> platillosDestacados) {
        this.platillosDestacados = platillosDestacados;
    }

    
}
