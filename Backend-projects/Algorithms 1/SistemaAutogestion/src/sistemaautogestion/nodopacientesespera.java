/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaautogestion;

/**
 *
 * @author Drakmalar
 */
public class nodopacientesespera {
    int ciPaciente;
    nodopacientesespera siguiente;

    public nodopacientesespera(int ciPaciente) {
        this.ciPaciente = ciPaciente;
        this.siguiente = null;
    }

    public int getCiPaciente() {
        return ciPaciente;
    }

    public void setCiPaciente(int ciPaciente) {
        this.ciPaciente = ciPaciente;
    }

    public nodopacientesespera getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodopacientesespera siguiente) {
        this.siguiente = siguiente;
    }
    
    
}
