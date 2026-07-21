/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import Sistemas.SistemaCochera;
import Sistemas.SistemaVehiculo;
import Sistemas.SistemaEstadia;
import java.util.ArrayList;

import Sistemas.SistemaParking;
import clasesDeEjemplo.Anomalia;
import clasesDeEjemplo.Cochera;
import clasesDeEjemplo.Estadia;
import clasesDeEjemplo.Parking;
import clasesDeEjemplo.Propietario;
import clasesDeEjemplo.Vehiculo;

/**
 *
 * @author Drakmalar
 */
public class Fachada {
    
    private static final Fachada instancia = new Fachada();
    
    public static Fachada getInstancia() {
        return instancia;
    }
    
    SistemaParking sistemaParking = new SistemaParking();
    public ArrayList<Parking> ObtenerParkings(){
        return sistemaParking.ObtenerParkings();
    }
    
    public Parking ObtenerParkingPorNombre(String nombreParking){
        return sistemaParking.ObtenerParkingPorNombre(nombreParking);
    }
    
    public Parking obtenerParkingPorCochera(String codigoCochera){
        return sistemaParking.obtenerParkingPorCochera(codigoCochera);
    }
    
    public void cargarListaParking(Parking parking){
        sistemaParking.cargarListaParking(parking);
    }
    
    public int obtenerCocherasOcupadas(Parking parking) {
        return sistemaParking.obtenerCocherasOcupadas(parking);
    }

    public int obtenerCocherasLibres(Parking parking) {
        return sistemaParking.obtenerCocherasLibres(parking);
    }
    
    
    SistemaEstadia sistemaEstadia = new SistemaEstadia();
    public ArrayList<Estadia> ObtenerEstadias(){
        return sistemaEstadia.ObtenerEstadias();
    }
    
    public Estadia ObtenerEstadiaPorCochera(String codigoCochera){
        return sistemaEstadia.ObtenerEstadiaPorCochera(codigoCochera);
    }
    
    public void cargarListaEstadia(Estadia estadia){
        sistemaEstadia.cargarListaEstadia(estadia);
    }
    
    SistemaAnomalia sistemaAnomalia = new SistemaAnomalia();
    public void cargarListaAnomalia(Anomalia anomalia){
        sistemaAnomalia.cargarListaAnomalia(anomalia);
    }
    
    public ArrayList<Anomalia> ObtenerAnomalias(){
        return sistemaAnomalia.ObtenerAnomalias();
    }
    
    public Cochera ObtenerCocheraDisponible(Parking parking){
        return sistemaParking.ObtenerCocheraDisponible(parking);
    }
    
    public void actualizarFactorDemanda() {
        sistemaParking.actualizarFactorDemanda();
    }
    
    SistemaCochera sistemaCochera = new SistemaCochera();
    public ArrayList<Cochera> ObtenerCochera(){
        return sistemaCochera.ObtenerCocheras();
    }
    
    public Cochera obtenerCocheraPorCodigo(String codigo){
        return sistemaCochera.obtenerCocheraPorCodigo(codigo);
    }
    
    public void cargarListaCochera(Cochera cochera){
        sistemaCochera.cargarListaCochera(cochera);
    }
    
    public void SetearEtiquetaCochera(Cochera cochera){
        sistemaCochera.SetearEtiquetaCochera(cochera);
    }
    
    SistemaVehiculo sistemaVehiculo = new SistemaVehiculo();
    public ArrayList<Vehiculo> ObtenerVehiculos(){
        return sistemaVehiculo.ObtenerVehiculos();
    }
    
    public void cargarListaVehiculo(Vehiculo vehiculo){
        sistemaVehiculo.cargarListaVehiculo(vehiculo);
    }
    
    public void SetearEtiquetaVehiculo(Vehiculo vehiculo){
        sistemaVehiculo.SetearEtiquetaVehiculo(vehiculo);
    }
    
    public Vehiculo obtenerVehiculoPorPatente(String patente){
        return sistemaVehiculo.obtenerVehiculoPorPatente(patente);
    }
    
    SistemaPropietario sistemaPropietario = new SistemaPropietario();
    public ArrayList<Propietario> ObtenerPropietarios(){
        return sistemaPropietario.ObtenerPropietarios();
    }
    
    public Propietario ObtenerPropietarioPorPatente(String patente){
        return sistemaPropietario.ObtenerPropietarioPorPatente(patente);
    }
    
    public void cargarListaPropietario(Propietario propietario){
        sistemaPropietario.cargarListaPropietario(propietario);
    }
    
    public void asignarVehiculoAPropietario(int inicio, int fin, ArrayList<Vehiculo> vehiculosPropietario, ArrayList<Vehiculo> vehiculosAux){
        
       sistemaPropietario.asignarVehiculoAPropietario(inicio, fin, vehiculosPropietario, vehiculosAux);
            
    }
    
}
