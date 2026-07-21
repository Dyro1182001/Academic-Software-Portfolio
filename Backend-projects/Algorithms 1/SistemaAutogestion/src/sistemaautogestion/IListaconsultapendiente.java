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
public interface IListaconsultapendiente {
    boolean EsVacia();
    public nodoconsultapendiente obtenerConsultasPorFecha(Date fecha);
    public void agregar(Date fecha);
    int cantnodos();
    boolean ExisteConsultaEnLaFecha(Date fecha);
    public void EliminarConsulta(Date fecha);
    public void eliminarUltimaConsulta();
    public String listar();
}
