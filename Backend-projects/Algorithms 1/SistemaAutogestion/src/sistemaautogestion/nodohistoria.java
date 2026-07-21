/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author alumnoFI
 */
public class nodohistoria {
    String detallesconsulta;
    Date fecha;
    
    nodohistoria siguiente;

    public nodohistoria(String detallesconsulta, Date fecha) {
        this.detallesconsulta = detallesconsulta;
        this.fecha = fecha;
        this.siguiente = null;
    }

    public nodohistoria getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodohistoria siguiente) {
        this.siguiente = siguiente;
    }

    public String getDetallesconsulta() {
        return detallesconsulta;
    }

    public void setDetallesconsulta(String detallesconsulta) {
        this.detallesconsulta = detallesconsulta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
