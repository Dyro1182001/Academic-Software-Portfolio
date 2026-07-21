/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import clasesDeEjemplo.Estadia;
import java.util.ArrayList;

/**
 *
 * @author Drakmalar
 */
public class SistemaEstadia {
    private ArrayList<Estadia> estadias = new ArrayList<Estadia>();
    
    public ArrayList<Estadia> ObtenerEstadias(){
        return estadias;
    }
    
    public Estadia ObtenerEstadiaPorCochera(String codigoCochera){
        for(Estadia e : estadias){
            if(e.getCochera().getCodigo().equals(codigoCochera)){
                return e;
            }
        }
        return null;
    }
    
    public void cargarListaEstadia(Estadia estadia){
        estadias.add(estadia);
    }
}
