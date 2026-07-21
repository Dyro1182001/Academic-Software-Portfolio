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
public interface IListaConsultas {
    boolean EsVacia();
    nodoconsultas obtenerconsultas(int ciPaciente);
    public nodoconsultas obtenerConsultasPorFecha(Date fecha);
    public void agregar(Date fecha);
    int cantnodos();
    public boolean HayConsulta (int ciPaciente, Date fecha);
    boolean ExisteConsultaEnLaFecha(Date fecha);
    public void EliminarConsulta(int ciPaciente, int tope);
    public String MostrarConsulta(String nombre, int nroReserva);
    public String MostrarPacientesEspera(Date fecha);
    int ObtenerCantPacientes(int i, int año);
}
