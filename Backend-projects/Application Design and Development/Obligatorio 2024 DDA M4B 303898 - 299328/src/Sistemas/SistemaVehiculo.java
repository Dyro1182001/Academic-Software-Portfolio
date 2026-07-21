/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import clasesDeEjemplo.Etiqueta;
import clasesDeEjemplo.Vehiculo;
import java.util.ArrayList;
import simuladortransito.SimuladorTransito;

/**
 *
 * @author Drakmalar
 */
public class SistemaVehiculo {
    private ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
    SimuladorTransito simulador = SimuladorTransito.getInstancia();
    
    public ArrayList<Vehiculo> ObtenerVehiculos(){
        return vehiculos;
    }
    
    public Vehiculo obtenerVehiculoPorPatente(String patente){
        for(Vehiculo v : vehiculos){
            if(v.getPatente().equals(patente)){
                return v;
            }
        }
        return null;
    }
    
    public void cargarListaVehiculo(Vehiculo vehiculo){
        vehiculos.add(vehiculo);
    }
    
    public void SetearEtiquetaVehiculo(Vehiculo vehiculo){
        ArrayList<Etiqueta> aux = vehiculo.getEtiquetas();
        Etiqueta discapacitado = new Etiqueta("DISCAPACITADO");
        Etiqueta empleado = new Etiqueta("EMPLEADO");
        Etiqueta electrico = new Etiqueta("ELECTRICO");
        for(int i = 0; i < aux.size(); i++){
            if(aux.get(i).equals(discapacitado)){
                vehiculo.esDiscapacitado();
            }
            if(aux.get(i).equals(empleado)){
                vehiculo.esEmpleado();
            }
            if(aux.get(i).equals(electrico)){
                vehiculo.esElectrico();
            }
        }
    }
}
