/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author Drakmalar
 */
public class nodoconsultapendiente {
    Date fecha;
    nodoconsultapendiente siguiente;

    public nodoconsultapendiente(Date fecha) {
        this.fecha = fecha;
        this.siguiente = null;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public nodoconsultapendiente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodoconsultapendiente siguiente) {
        this.siguiente = siguiente;
    }
}
