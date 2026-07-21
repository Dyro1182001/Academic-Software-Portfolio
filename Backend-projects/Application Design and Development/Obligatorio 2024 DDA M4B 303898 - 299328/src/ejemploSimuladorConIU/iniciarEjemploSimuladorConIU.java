/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemploSimuladorConIU;

import Controladores.ParkingController;
import Vistas.VistaTableroControl;
import clasesDeEjemplo.DatosPrueba;
import clasesDeEjemplo.Propietario;
import clasesDeEjemplo.SensorDePrueba;
import java.util.ArrayList;
import simuladorIU.SimuladorIU;
import simuladortransito.Estacionable;
import simuladortransito.Transitable;

/**
 *
 * @author PC
 */
public class iniciarEjemploSimuladorConIU {

    public static void main(String[] args) {

        SensorDePrueba sensor = new SensorDePrueba();
        ArrayList<Estacionable> cocheras = DatosPrueba.getCocheras(25);
        ArrayList<Transitable> vehiculos = DatosPrueba.getVehiculos(157);
        ArrayList<Propietario> propietarios = DatosPrueba.getPropietarios(50);
        new SimuladorIU(null, false, sensor, cocheras, vehiculos).setVisible(true);
        new VistaTableroControl().setVisible(true);

    }

}
