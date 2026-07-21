/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import Sistemas.Fachada;
import java.util.ArrayList;
import simuladortransito.Estacionable;
import simuladortransito.Transitable;
import java.util.Arrays;
import clasesDeEjemplo.Etiqueta;
import clasesDeEjemplo.Parking;
import java.util.Random;

/**
 *
 * @author PC
 */
public class DatosPrueba {

    private static Etiqueta[] etiqueta = {new Etiqueta("DISCAPACITADO"), new Etiqueta("ELECTRICO"), new Etiqueta("EMPLEADO")};

    private static String[] tipo = {"MOTOCICLETA", "STANDARD", "CARGA", "PASAJEROS"};

    private static String[] nombre = {"Juan", "Maria", "Alejandro", "Albert", "Robert", "Bruno", "Carlos", "Gonzalo", "Santiago"};

    private static String[] apellido = {"Alcarraz", "Delgado", "Gonzalez", "Iglesias", "Moreira", "Hohenheim", "Martinez", "Armstrong", "Liebert"};

    public static ArrayList<Estacionable> getCocheras(int cuantos) {
        ArrayList<Estacionable> lista = new ArrayList<>();
        Fachada fachada = Fachada.getInstancia();
        Parking parking1 = crearParking("Parking1", "Direccion1", fachada);
        Parking parking2 = crearParking("Parking2", "Direccion2", fachada);

        for (int x = 0; x < cuantos; x++) {
            ArrayList<Etiqueta> auxEtiqueta = new ArrayList<>();
            if (x % 5 == 0) {
                auxEtiqueta.add(etiqueta[x % etiqueta.length]);
            }
            
            Cochera cochera1 = crearCochera("C" + x + "P1", auxEtiqueta, parking1, fachada);
            Cochera cochera2 = crearCochera("C" + x + "P2", auxEtiqueta, parking2, fachada);

            lista.add(cochera1);
            lista.add(cochera2);
        }
        return lista;
    }

    private static Parking crearParking(String nombre, String direccion, Fachada fachada) {
        Parking parking = new Parking(nombre, direccion, new ArrayList<>(), new Tarifario(0.05, 0.1, 0.1, 0.1));
        fachada.cargarListaParking(parking);
        return parking;
    }

    private static Cochera crearCochera(String nombre, ArrayList<Etiqueta> etiquetas, Parking parking, Fachada fachada) {
        Cochera cochera = new Cochera(nombre, etiquetas);
        fachada.SetearEtiquetaCochera(cochera);
        parking.agregarCochera(cochera);
        fachada.cargarListaCochera(cochera);
        return cochera;
    }

    public static ArrayList<Transitable> getVehiculos(int cuantos) {
        ArrayList<Transitable> lista = new ArrayList<>();
        Fachada fachada = Fachada.getInstancia();

        for (int x = 0; x < cuantos; x++) {
            ArrayList<Etiqueta> auxEtiqueta = new ArrayList<>();
            if (x % 5 == 0) {
                auxEtiqueta.add(etiqueta[x % etiqueta.length]);
            }

            Vehiculo vehiculo = new Vehiculo("M" + x, tipo[x % 4], auxEtiqueta);
            lista.add(vehiculo);
            fachada.cargarListaVehiculo(vehiculo);
        }
        return lista;
    }

    public static ArrayList<Propietario> getPropietarios(int cuantos) {
        ArrayList<Propietario> lista = new ArrayList<>();
        Fachada fachada = Fachada.getInstancia();
        ArrayList<Vehiculo> vehiculosAux = fachada.ObtenerVehiculos();
        Random random = new Random();

        int totalVehiculos = vehiculosAux.size();
        int vehiculosPorPropietario = (int) Math.ceil((double) totalVehiculos / cuantos);

        int inicio = 0;

        for (int x = 0; x < cuantos; x++) {
            ArrayList<Vehiculo> vehiculosPropietario = new ArrayList<>();

            int fin = Math.min(inicio + vehiculosPorPropietario, totalVehiculos);

            fachada.asignarVehiculoAPropietario(inicio, fin, vehiculosPropietario, vehiculosAux);

            Propietario propietario = new Propietario(x + "247" + x + "83" + x, nombre[x % 9] + "-" + apellido[x % 9], random.nextInt(111) - 10, vehiculosPropietario);
            lista.add(propietario);
            fachada.cargarListaPropietario(propietario);

            inicio = fin;
        }

        return lista;
    }

}
