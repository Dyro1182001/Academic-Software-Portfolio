/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author Drakmalar
 */
public interface IListadiaconsulta {
    boolean EsVacia();
    public nododiaconsulta obtenerConsultasPorFecha(Date fecha);
    public void agregar(Date fecha);
    String listar();
    int cantnodos();
}
