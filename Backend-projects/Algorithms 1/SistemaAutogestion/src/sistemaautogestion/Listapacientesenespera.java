/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author alumnoFI
 */
public class Listapacientesenespera implements IListaenespera {

    nodopacientesespera primero;
    nodopacientesespera ultimo;

    public nodopacientesespera getUltimo() {
        return ultimo;
    }

    public void setUltimo(nodopacientesespera ultimo) {
        this.ultimo = ultimo;
    }
    int cantnodos;
    
    public Listapacientesenespera() {
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nodopacientesespera getPrimero() {
        return primero;
    }

    public void setPrimero(nodopacientesespera primero) {
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
    public void agregar(int ci) {
        
            nodopacientesespera nuevo = new nodopacientesespera(ci);
            
            if (this.EsVacia()) {
                this.setPrimero(nuevo);
                this.setUltimo(nuevo);
            } else {
                nuevo.setSiguiente(this.getPrimero());
                this.setPrimero(nuevo);
            }
            
            this.cantnodos = this.cantnodos + 1 ;
    }


    @Override
    public String listar() {
        nodopacientesespera aux=this.getPrimero();
        
        String retornar = "\n";
        
        while (aux!=null){
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
    }
    
    @Override
    public void eliminarEnEspera(int ciPaciente){
        boolean fueBorrado = false;
        if(this.obtenerpacientesEnEspera(ciPaciente) != null){
            nodopacientesespera espera = this.getPrimero();
            if(this.getPrimero().ciPaciente == ciPaciente){
                if(!this.EsVacia()){
                    this.setPrimero(this.primero.getSiguiente());
                    this.cantnodos = this.cantnodos - 1;
                }
                
            }else if(this.getUltimo().ciPaciente == ciPaciente){
                if(!this.EsVacia()){
                    if(this.cantnodos == 1){
                        this.setPrimero(null);
                        this.setUltimo(null);
                    }else{
                        nodopacientesespera segundoespera = this.getPrimero();
                        while(segundoespera.getSiguiente() != this.getUltimo()){
                            segundoespera = segundoespera.getSiguiente();
                        }
                        segundoespera.setSiguiente(null);
                        this.setUltimo(segundoespera);
                    }
                    this.cantnodos = this.cantnodos - 1;
                }
            }else{
                while(espera.getSiguiente() != null || espera.getSiguiente() != this.getUltimo() || !fueBorrado){
                    if(espera.getSiguiente().ciPaciente == ciPaciente){
                        espera.setSiguiente(espera.getSiguiente().getSiguiente());
                        fueBorrado = true;
                        this.cantnodos = this.cantnodos - 1;
                    }
                    espera = espera.getSiguiente();
                }
            }  
        } 
        
    }

    @Override
    public int cantnodos() {
        return this.getCantnodos();
    }

    @Override
    public nodopacientesespera obtenerpacientesEnEspera(int ciPaciente) {
       nodopacientesespera encontrado = null;
        if (!this.EsVacia()) {
            nodopacientesespera aux = this.getPrimero();
            while (aux != null && encontrado == null) {
                if (aux.ciPaciente == ciPaciente) {
                    encontrado = aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return encontrado;
    }

}
