/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import clasesDeEjemplo.Propietario;
import clasesDeEjemplo.Vehiculo;
import java.util.ArrayList;

/**
 *
 * @author Drakmalar
 */
public class SistemaPropietario {
    private ArrayList<Propietario> propietarios = new ArrayList<Propietario>();
    
    public ArrayList<Propietario> ObtenerPropietarios(){
        return propietarios;
    }
    
    public Propietario ObtenerPropietarioPorPatente(String patente){
        for(Propietario p : propietarios){
            if(p.getVehiculoPorPatente(patente).getPatente() == null ? patente == null : p.getVehiculoPorPatente(patente).getPatente().equals(patente)){
                return p;
            }
        }
        return null;
    }
    
     public void cargarListaPropietario(Propietario propietario){
        propietarios.add(propietario);
    }
     
    public void asignarVehiculoAPropietario(int inicio, int fin, ArrayList<Vehiculo> vehiculosPropietario, ArrayList<Vehiculo> vehiculosAux){
        for (int i = inicio; i < fin; i++) {
            vehiculosPropietario.add(vehiculosAux.get(i));
       }
    }
}
