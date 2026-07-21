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
public class Listahistoria implements IListahistoria {

    
    nodohistoria primero;
    nodohistoria ultimo;
    int cantnodos;
    
    public Listahistoria() {
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nodohistoria getPrimero() {
        return primero;
    }

    public void setPrimero(nodohistoria primero) {
        this.primero = primero;
    }

    public nodohistoria getUltimo() {
        return ultimo;
    }

    public void setUltimo(nodohistoria ultimo) {
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

    @Override
    public void agregar(String detallesconsulta, Date fecha) {
        nodohistoria nuevo = new nodohistoria(detallesconsulta, fecha);

    if (this.esVacia()) {
        nuevo.setSiguiente(this.getPrimero());
        this.setPrimero(nuevo);
        this.setUltimo(nuevo);
    } else {
        nodohistoria actual = this.getPrimero();
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }

    this.cantnodos++;
    }

    @Override
    public String listar() {
        nodohistoria aux=this.getPrimero();
        
        String retornar = "\n";
        
        while (aux!=null){
            retornar = retornar + aux.getFecha()+ " - " +aux.getDetallesconsulta() + "\n";
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
    }

    @Override
    public int cantnodos() {
        return this.getCantnodos();
    }
    
}
