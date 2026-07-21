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
public class nodoconsultas {
    Date fecha;
    nodoconsultas siguiente;
    ListapacientesAgendados lpa;
    Listapacientesenespera le;
    
    public nodoconsultas(Date fecha) {
        this.fecha = fecha;
        this.siguiente = null;
        this.lpa = new ListapacientesAgendados();
        this.le = new Listapacientesenespera();
    }
    
    public ListapacientesAgendados getLpa() {
        return lpa;
    }

    public void setLpa(ListapacientesAgendados lpa) {
        this.lpa = lpa;
    }

    public Listapacientesenespera getLe() {
        return le;
    }

    public void setLe(Listapacientesenespera le) {
        this.le = le;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public nodoconsultas getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodoconsultas siguiente) {
        this.siguiente = siguiente;
    }
    
}
