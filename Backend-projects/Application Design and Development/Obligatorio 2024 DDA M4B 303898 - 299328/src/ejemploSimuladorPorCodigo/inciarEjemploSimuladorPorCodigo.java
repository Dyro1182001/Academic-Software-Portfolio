/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemploSimuladorPorCodigo;

import clasesDeEjemplo.DatosPrueba;
import clasesDeEjemplo.SensorDePrueba;
import simuladortransito.ConfiguracionException;
import simuladortransito.FlujoEgreso;
import simuladortransito.FlujoIngreso;
import simuladortransito.Periodo;
import simuladortransito.SimuladorTransito;

/**
 *
 * @author PC
 */
public class inciarEjemploSimuladorPorCodigo {

    public static void main(String[] args) {
        // TODO code application logic here
        new inciarEjemploSimuladorPorCodigo().iniciar();
    }

    public void iniciar() {
        SimuladorTransito simulador = SimuladorTransito.getInstancia();
        //Cargo vehiculos y cocheras
        simulador.addEstacionables(DatosPrueba.getCocheras(20));
        simulador.addTransitables(DatosPrueba.getVehiculos(20));
        try {
            //Agrego ingresos y egresos
            simulador.programar(new FlujoIngreso("I1", new Periodo(3, 10), 20));
            simulador.programar(new FlujoEgreso("E1", new Periodo(15, 10), 20));
            //INICIO SIMULACION
            simulador.iniciar(new SensorDePrueba());
        } catch (ConfiguracionException ex) {
            ex.printStackTrace();
        }

    }

}
