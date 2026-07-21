
package sistemaautogestion;

public interface IListapacientes {
    boolean esVacia();
    void agregar(String nombrep, int nrop, String direccion);
    public void eliminarUltimoPaciente();
    void eliminar(int nrop);
    nodopaciente obtenerpaciente(int nrop);
    String listar();
    int cantnodos();  
}
