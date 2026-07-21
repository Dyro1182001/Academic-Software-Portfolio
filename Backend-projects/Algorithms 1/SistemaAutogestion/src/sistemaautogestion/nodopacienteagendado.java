/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaautogestion;

/**
 *
 * @author Drakmalar
 */
public class nodopacienteagendado {
    int ci;
    nodopacienteagendado siguiente;
    int nroReserva;
    static int ultimoNroReserva = 1;
    String estadoConsulta;

    public nodopacienteagendado(int ci) {
        this.ci = ci;
        this.siguiente = null;
        this.nroReserva = ultimoNroReserva;
        this.ultimoNroReserva++;
        this.estadoConsulta = "";
    }

    public String getEstadoConsulta() {
        return estadoConsulta;
    }

    public void setEstadoConsulta(String estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }

    public int getNroReserva() {
        return nroReserva;
    }

    public void setNroReserva(int nroReserva) {
        this.nroReserva = nroReserva;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public nodopacienteagendado getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodopacienteagendado siguiente) {
        this.siguiente = siguiente;
    }
    
}
