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
public class ListaDiaConsulta implements IListadiaconsulta {
    nododiaconsulta primero;
    nododiaconsulta ultimo;
    
    int cantnodos;

    public nododiaconsulta getUltimo() {
        return ultimo;
    }

    public void setUltimo(nododiaconsulta ultimo) {
        this.ultimo = ultimo;
    }
    
    public ListaDiaConsulta() {
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nododiaconsulta getPrimero() {
        return primero;
    }

    public void setPrimero(nododiaconsulta primero) {
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
        nododiaconsulta nuevo = new nododiaconsulta(fecha);

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
    public String listar() {
        nododiaconsulta aux=this.getPrimero();
        
        String retornar = "\n";
        
        while (aux!=null){
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
        
    }

    @Override
    public int cantnodos() {
        return this.getCantnodos();
    }
    
    @Override
    public nododiaconsulta obtenerConsultasPorFecha(Date fecha) {
       nododiaconsulta encontrado = null;
        if (!this.EsVacia()) {
           
            nododiaconsulta aux= this.primero;
            
            while (aux != null && encontrado == null) {
                if (aux.getFecha().equals(fecha))
                {
                    encontrado = aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return encontrado;
    }
}
