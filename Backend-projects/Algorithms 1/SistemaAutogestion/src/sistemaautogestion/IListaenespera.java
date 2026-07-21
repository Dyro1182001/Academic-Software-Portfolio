/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author alumnoFI
 */
public interface IListaenespera {
    boolean EsVacia();
    nodopacientesespera obtenerpacientesEnEspera(int ciPaciente);
    String listar();
    int cantnodos();
    public void agregar(int ci);
    public void eliminarEnEspera(int ciPaciente);
}
