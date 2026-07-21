/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import clasesDeEjemplo.Cochera;
import clasesDeEjemplo.Etiqueta;
import java.util.ArrayList;
import simuladortransito.Estacionable;

/**
 *
 * @author Drakmalar
 */
public class SistemaCochera {
    private ArrayList<Cochera> cocheras = new ArrayList<Cochera>();
    
    public ArrayList<Cochera> ObtenerCocheras(){
        return cocheras;
    }
    
    public Cochera obtenerCocheraPorCodigo(String codigo){
        for(Cochera c : cocheras){
            if(c.getCodigo().equals(codigo)){
                return c;
            }
        }
        return null;
    }
    
    public void cargarListaCochera(Cochera cochera){
        cocheras.add(cochera);
    }
    
    public void SetearEtiquetaCochera(Cochera cochera){
        ArrayList<Etiqueta> aux = cochera.getEtiquetas();
        Etiqueta discapacitado = new Etiqueta("DISCAPACITADO");
        Etiqueta empleado = new Etiqueta("EMPLEADO");
        Etiqueta electrico = new Etiqueta("ELECTRICO");
        for(int i = 0; i < aux.size(); i++){
            if(aux.get(i).equals(discapacitado)){
                cochera.esDiscapacitado();
            }
            if(aux.get(i).equals(empleado)){
                cochera.esEmpleado();
            }
            if(aux.get(i).equals(electrico)){
                cochera.esElectrico();
            }
        }
    }
}
