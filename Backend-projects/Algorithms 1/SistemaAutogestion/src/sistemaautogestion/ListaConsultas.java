/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author Drakmalar
 */
public class ListaConsultas implements IListaConsultas {
    
    nodoconsultas primero;
    nodoconsultas ultimo;
    
    int cantnodos;

    public nodoconsultas getUltimo() {
        return ultimo;
    }

    public void setUltimo(nodoconsultas ultimo) {
        this.ultimo = ultimo;
    }
    
    public ListaConsultas() {
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nodoconsultas getPrimero() {
        return primero;
    }

    public void setPrimero(nodoconsultas primero) {
        this.primero = primero;
    }

    public int getCantnodos() {
        return cantnodos;
    }

    public void setCantnodos(int cantnodos) {
        this.cantnodos = cantnodos;
    }

    @Override
    public boolean EsVacia() {
       return this.getPrimero() == null;
    }
    
    @Override
    public void agregar(Date fecha) {
        nodoconsultas nuevo = new nodoconsultas(fecha);

        if(this.EsVacia()){
            this.setPrimero(nuevo);
            this.setUltimo(nuevo);
        }else{
            this.ultimo.setSiguiente(nuevo);
            this.setUltimo(nuevo);
        }

        this.cantnodos = this.cantnodos + 1;
    }
   
    @Override
    public int cantnodos() {
        return this.getCantnodos();
    }
    
    @Override
    public nodoconsultas obtenerconsultas(int ciPaciente) {
    nodoconsultas encontrado = null;
    if (!this.EsVacia()) {
        nodoconsultas aux = this.getPrimero();

        while (aux != null && encontrado == null) {
            nodopacienteagendado agendado = aux.lpa.obtenerpaciente(ciPaciente);
            if (agendado != null && agendado.getCi() == ciPaciente) {
                encontrado = aux;
            }
            aux = aux.getSiguiente();
        }
    }
    return encontrado;
    }
    
    @Override
    public nodoconsultas obtenerConsultasPorFecha(Date fecha) {
       nodoconsultas encontrado = null;
        if (!this.EsVacia()) {
           
            nodoconsultas aux= this.primero;
            
            while (aux != null) {
                if (aux.getFecha().equals(fecha))
                {
                    encontrado = aux;
                    return encontrado;
                }
                aux = aux.getSiguiente();
            }
        }
        return encontrado;
    }
    
    @Override
    public boolean HayConsulta (int ciPaciente, Date fecha){
        boolean resultado = false;
        
        nodoconsultas aux = this.getPrimero();
        while(aux != null){
            if(aux.getFecha().equals(fecha)){
                nodopacienteagendado nuevo = aux.lpa.getPrimero();
                while(nuevo != null){
                    if(nuevo.getCi() == ciPaciente){
                        resultado = true;
                        return resultado;
                    }
                    nuevo = nuevo.getSiguiente();
                }
            }
            aux = aux.getSiguiente();
        }
        return resultado;
    }

    @Override
    public boolean ExisteConsultaEnLaFecha(Date fecha) {
        boolean existe = false;
        nodoconsultas aux = this.getPrimero();
        while(aux != null){
            if(aux.getFecha().equals(fecha)){
                existe = true;
                return existe;
            }
            aux = aux.getSiguiente();
        }
        return existe;
    }
    
    @Override
    public void EliminarConsulta(int ciPaciente, int tope){
        boolean fueBorrado = false;
        nodoconsultas consulta = this.getPrimero();
        while(consulta != null && !fueBorrado){
            fueBorrado = consulta.lpa.eliminarAgendado(ciPaciente);
            if(fueBorrado){
                consulta.lpa.agregar(ciPaciente, consulta, tope);
                consulta.le.eliminarEnEspera(ciPaciente);
            }
            consulta = consulta.getSiguiente(); 
        }
    }
    
    @Override
    public String MostrarConsulta(String nombre, int nroReserva){
        String aux = "";
        
        aux = "\n" + "Medico: " + nombre + " Número de reserva: " + nroReserva;
        
        return aux;
    }
    
    @Override
    public String MostrarPacientesEspera(Date fecha){
        String aux = "";
        String aux2 = "";
        nodoconsultas consulta = this.getPrimero();
        while(consulta != null){
            if(consulta.getFecha().equals(fecha)){
                aux = "\n" + aux + " Fecha: " + consulta.getFecha() + "\n";
                aux2 = aux2 + consulta.lpa.MostrarPacientes();
            }
            consulta = consulta.getSiguiente();
        }
        return aux + aux2;
    } 
    
    @Override
    public int ObtenerCantPacientes(int i, int año){
        int aux = 0;
        nodoconsultas consulta = this.getPrimero();
        while(consulta != null){
            int fecha = consulta.getFecha().getYear()+1900;
            if(consulta.getFecha().getDate() == i && fecha == año){
                aux = aux + consulta.lpa.ObtenerCantPacientes();
            }
            consulta = consulta.getSiguiente();
        }
        return aux;
    }
    
    
}
