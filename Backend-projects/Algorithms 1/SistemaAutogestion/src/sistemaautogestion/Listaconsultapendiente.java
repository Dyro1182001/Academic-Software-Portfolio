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
public class Listaconsultapendiente implements IListaconsultapendiente {
    
     nodoconsultapendiente primero;
     nodoconsultapendiente ultimo;
    
    int cantnodos;

    public nodoconsultapendiente getUltimo() {
        return ultimo;
    }

    public void setUltimo(nodoconsultapendiente ultimo) {
        this.ultimo = ultimo;
    }
    
    public Listaconsultapendiente() {
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nodoconsultapendiente getPrimero() {
        return primero;
    }

    public void setPrimero(nodoconsultapendiente primero) {
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
        nodoconsultapendiente nuevo = new nodoconsultapendiente(fecha);

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
    public nodoconsultapendiente obtenerConsultasPorFecha(Date fecha) {
       nodoconsultapendiente encontrado = null;
        if (!this.EsVacia()) {
           
            nodoconsultapendiente aux = this.primero;
            
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
    public boolean ExisteConsultaEnLaFecha(Date fecha) {
        boolean existe = false;
        nodoconsultapendiente aux = this.getPrimero();
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
    public void EliminarConsulta(Date fecha){
        if (!this.EsVacia()) {
             nodoconsultapendiente encontrado = this.obtenerConsultasPorFecha(fecha);
            if (encontrado != null) {
                if (this.cantnodos == 1) {
                    this.setPrimero(null);
                    this.setCantnodos(0);
                } else {
                    nodoconsultapendiente aux = this.getPrimero();
                    if (this.getPrimero().getFecha()== fecha) {
                        this.setPrimero(this.getPrimero().getSiguiente());
                        this.cantnodos = this.cantnodos - 1;
                    }else if(this.getUltimo().getFecha() == fecha){
                        eliminarUltimaConsulta();
                    } else {
                        while (aux.getSiguiente() != null) {
                            if (aux.getSiguiente().getFecha() == fecha) {
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
    public void eliminarUltimaConsulta(){
        nodoconsultapendiente aux = this.getPrimero();
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
    public String listar() {
        nodoconsultapendiente aux=this.getPrimero();
        
        String retornar = "\n";
        
        while (aux!=null){
            retornar += "Fecha: " + aux.fecha + "\n";
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
    }
}
