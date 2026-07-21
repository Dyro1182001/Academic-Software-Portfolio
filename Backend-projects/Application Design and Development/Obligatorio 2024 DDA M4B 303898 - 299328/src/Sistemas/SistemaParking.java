/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import clasesDeEjemplo.Cochera;
import clasesDeEjemplo.Parking;
import java.util.ArrayList;

/**
 *
 * @author Drakmalar
 */
public class SistemaParking {
    
    private ArrayList<Parking> parkings = new ArrayList<Parking>();
    
    public ArrayList<Parking> ObtenerParkings(){
        return parkings;
    }
    
    public Parking ObtenerParkingPorNombre(String nombreParking){
        for(Parking p : parkings){
            if(p.getNombre().equals(nombreParking)){
                return p;
            }
        }
        return null;
    }
    
    public Parking obtenerParkingPorCochera(String codigoCochera){
        
        for(Parking p : parkings){
            if(p.DevolverCochera(codigoCochera).getCodigo() == null ? codigoCochera == null : p.DevolverCochera(codigoCochera).getCodigo().equals(codigoCochera)){
                return p;
            }
        }
        return null;
    }
    
    public Cochera ObtenerCocheraDisponible(Parking parking){
        for(Cochera c : parking.getCocheras()){
            if(!c.isOcupada()){
                return c;
            }
        }
        return null;
    }
    
    public void actualizarFactorDemanda() {
        for (Parking parking : parkings) {
            int ingresos = (int) parking.getEstadias().stream().filter(e -> e.getFechaHoraSalida() == null).count();
            int egresos = (int) parking.getEstadias().stream().filter(e -> e.getFechaHoraSalida() != null).count();
            parking.actualizarFactorDemanda(ingresos, egresos);
        }
    }
    
    public void cargarListaParking(Parking parking){
        parkings.add(parking);
    }
    
    public int obtenerCocherasOcupadas(Parking parking) {
        for(Parking p : parkings){
            if(parking.getNombre().equals(p.getNombre())){
                return p.getCocherasOcupadas();
            }
        }
        return 0;
    }

    public int obtenerCocherasLibres(Parking parking) {
        for(Parking p : parkings){
            if(parking.getNombre().equals(p.getNombre())){
                return p.getCocherasLibres();
            }
        }
        return 0;
    }
    
    
}
