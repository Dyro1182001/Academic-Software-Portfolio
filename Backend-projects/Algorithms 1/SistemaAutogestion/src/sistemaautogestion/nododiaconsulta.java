/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author alumnoFI
 */
public class nododiaconsulta {
    Date fecha;
    nododiaconsulta siguiente;
    Listapaciente lp;
    Listapacientesenespera lpes;

    public nododiaconsulta getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nododiaconsulta siguiente) {
        this.siguiente = siguiente;
    }
    
    public nododiaconsulta(Date fecha) {
        this.fecha = fecha;
        this.siguiente = null;
        this.lp = new Listapaciente();
        this.lpes = new Listapacientesenespera();
    }

    public Listapaciente getLp() {
        return lp;
    }

    public void setLp(Listapaciente lp) {
        this.lp = lp;
    }

    public Listapacientesenespera getLpes() {
        return lpes;
    }

    public void setLpes(Listapacientesenespera lpes) {
        this.lpes = lpes;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
