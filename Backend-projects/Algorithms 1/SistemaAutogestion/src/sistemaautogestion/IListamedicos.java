
package sistemaautogestion;

import java.util.Date;

public interface IListamedicos {
    boolean esVacia();

    void agregar(String nombre,int codMedico,int tel, int especialidad);
    public void eliminarUltimoMedico();
    void eliminar(int nrom);
    nodomedico obtenermedico(int nrom);
    String listar();
    int cantnodos();
    int ObtenerCantidadDePacientes(int i, int j, int año);
}
