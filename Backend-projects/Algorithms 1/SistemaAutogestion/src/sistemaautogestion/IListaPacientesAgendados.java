/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemaautogestion;

/**
 *
 * @author Drakmalar
 */
public interface IListaPacientesAgendados {
    boolean esVacia();
    public void eliminarUltimoPaciente();
    void eliminar(int nrop);
    nodopacienteagendado obtenerpaciente(int nrop);
    String listar();
    int cantnodos();
    public boolean eliminarAgendado(int ciPaciente);
    public String MostrarPacientes();
    int ObtenerCantPacientes();
}
