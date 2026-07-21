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
public interface IListahistoria {
    boolean esVacia();
    void agregar(String detallesconsulta, Date fecha);
    String listar();
    int cantnodos();
}
