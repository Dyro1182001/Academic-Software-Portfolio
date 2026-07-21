/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaautogestion;

/**
 *
 * @author Drakmalar
 */
public class ListapacientesAgendados implements IListaPacientesAgendados {
    nodopacienteagendado primero;
    nodopacienteagendado ultimo;
    int cantnodos;
    
    public ListapacientesAgendados(){
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nodopacienteagendado getPrimero() {
        return primero;
    }

    public void setPrimero(nodopacienteagendado primero) {
        this.primero = primero;
    }

    public nodopacienteagendado getUltimo() {
        return ultimo;
    }

    public void setUltimo(nodopacienteagendado ultimo) {
        this.ultimo = ultimo;
    }

    public int getCantnodos() {
        return cantnodos;
    }

    public void setCantnodos(int cantnodos) {
        this.cantnodos = cantnodos;
    }

    @Override
    public boolean esVacia() {
        return this.getPrimero() == null;
    }

    void agregar (int ciPaciente, nodoconsultas consulta, int tope){
        if(this.cantnodos() >= tope){
            consulta.le.agregar(ciPaciente);
        }else{
            nodopacienteagendado aux = new nodopacienteagendado(ciPaciente);
            if (this.esVacia()) {
                this.setPrimero(aux);
                this.setUltimo(aux);
            } else {
                aux.setSiguiente(this.getPrimero());
                this.setPrimero(aux);
            }
            
            this.cantnodos = this.cantnodos + 1 ;
            
        }
    }

    @Override
    public void eliminarUltimoPaciente(){
        nodopacienteagendado aux = this.getPrimero();
        
        if(this.cantnodos == 1){
            this.setPrimero(null);
            this.setUltimo(null);
        }else if(this.cantnodos > 1){
            while(aux.getSiguiente() != this.getUltimo()){
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(null);
        }
        
        if(this.cantnodos >0){
            this.cantnodos = this.cantnodos - 1;
        }
    }

    
    @Override
    public void eliminar(int nrop) {
        if (!this.esVacia()) {
            nodopacienteagendado encontrado = this.obtenerpaciente(nrop);
            if (encontrado != null) {
                if (this.cantnodos == 1) {
                    this.setPrimero(null);
                    this.setCantnodos(0);
                } else {
                    nodopacienteagendado aux = this.getPrimero();
                    if (this.getPrimero().getCi() == nrop) {
                        this.setPrimero(this.getPrimero().getSiguiente());
                        this.cantnodos = this.cantnodos - 1;
                    }else if(this.getUltimo().getCi() == nrop){
                        eliminarUltimoPaciente();
                    } else {
                        while (aux.getSiguiente() != null) {
                            if (aux.getSiguiente().getCi() == nrop) {
                                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                                this.cantnodos = this.cantnodos - 1;
                            }
                            aux = aux.getSiguiente();

                        }
                    }
                }

            } else {

                System.out.println("El elemento buscado no esta en la lista");
            }

        } else {

            System.out.println("Esta vacia");
        }

    }

    @Override
    public nodopacienteagendado obtenerpaciente(int nrop) {
        boolean encontrado = false;
        nodopacienteagendado paciente = null;
        if (!this.esVacia()) {
            nodopacienteagendado aux = this.getPrimero();
            while (aux != null && !encontrado) {
                if (aux.getCi() == nrop) {
                    paciente = aux;
                    return paciente;
                }
                aux = aux.getSiguiente();
            }
        }
        return paciente;
    }
    
    

    @Override
    public String listar() {
        nodopacienteagendado aux=this.getPrimero();
        
        String retornar = "\n";
        
        while (aux!=null){
            retornar += "Paciente: " + aux.getCi() + "\n";
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
    }
    
    @Override
    public boolean eliminarAgendado(int ciPaciente) {
    if (this.esVacia()) {
        return false; 
    }

    boolean fueBorrado = false;
    nodopacienteagendado agendado = this.getPrimero();

    if (agendado.getCi() == ciPaciente) {
        this.setPrimero(this.primero.getSiguiente());
        this.cantnodos--;
        fueBorrado = true;
        return true; 
    } else if (this.getUltimo().getCi() == ciPaciente) {
        if (this.cantnodos == 1) {
            this.setPrimero(null);
            this.setUltimo(null);
        } else {
            nodopacienteagendado segundoagendado = this.getPrimero();
            while (segundoagendado.getSiguiente() != this.getUltimo()) {
                segundoagendado = segundoagendado.getSiguiente();
            }
            segundoagendado.setSiguiente(null);
            this.setUltimo(segundoagendado);
        }
        this.cantnodos--;
        return true; 
    } else {
        while (agendado.getSiguiente() != null && !fueBorrado) {
            if (agendado.getSiguiente().getCi() == ciPaciente) {
                agendado.setSiguiente(agendado.getSiguiente().getSiguiente());
                this.cantnodos--;
                fueBorrado = true;
            }
            agendado = agendado.getSiguiente();
        }
    }

    return fueBorrado;
    }

    
    @Override
    public String MostrarPacientes(){
        String aux = "";
        
        nodopacienteagendado paciente = this.getPrimero();
        while(paciente != null){
            if(paciente.getEstadoConsulta() == "en espera"){
                aux = "\n" + aux + " Paciente: " + paciente.getCi() + " Nro de Reserva: " + paciente.getNroReserva() + "\n";
            }
            paciente = paciente.getSiguiente();
        }
        
        return aux;
    }
    

    @Override
    public int cantnodos() {
        return this.getCantnodos();
    }

    @Override
    public int ObtenerCantPacientes() {
        int aux = 0;
        nodopacienteagendado paciente = this.getPrimero();
        while(paciente != null){
            if(paciente.getEstadoConsulta() == "terminada"){
                aux = aux + 1;
            } 
            paciente = paciente.getSiguiente();
        }
        return aux;
    }
}
