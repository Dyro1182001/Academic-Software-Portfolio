package clasesDeEjemplo;

import Sistemas.Fachada;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import simuladortransito.Estacionable;
import simuladortransito.Sensor;
import simuladortransito.Transitable;


public class SensorDePrueba implements Sensor {

    Fachada fachada = Fachada.getInstancia();

    @Override
    public void ingreso(Transitable transitable, Estacionable estacionable) {
        Vehiculo v = (Vehiculo) transitable;
        Cochera c = (Cochera) estacionable;

        if (v != null && c != null) {
            if (c.isOcupada()) {
                Estadia estadia = fachada.ObtenerEstadiaPorCochera(c.getCodigo());
                estadia.setFechaHoraEntrada(null);
                estadia.setFechaHoraSalida(null);
                estadia.setValorFacturado(0);
                Anomalia anomalia = new Anomalia(LocalDateTime.now(), "HOUDINI", estadia);
                fachada.cargarListaAnomalia(anomalia);
            }

            c.setOcupada(true);
            v.setEstacionado(true);
            LocalDateTime fechaEntrada = LocalDateTime.now();
            Estadia estadia = new Estadia(fechaEntrada, c, v);
            fachada.cargarListaEstadia(estadia);
            Parking parking = fachada.obtenerParkingPorCochera(c.getCodigo());
            parking.agregarEstadia(estadia);
            fachada.actualizarFactorDemanda();
        }
    }

    @Override
    public void egreso(Transitable transitable, Estacionable estacionable) {
        LocalDateTime ahora = LocalDateTime.now();
        Vehiculo v = (Vehiculo) transitable;
        Cochera c = (Cochera) estacionable;
        Parking parking = fachada.obtenerParkingPorCochera(c.getCodigo());
        Estadia estadia = fachada.ObtenerEstadiaPorCochera(c.getCodigo());
        if (estadia == null) {
            System.err.println("Error: No se encontró la estadía para la cochera " + c.getCodigo());
            return;
        }
        if (estadia.getFechaHoraSalida() == null) {
            estadia.setFechaHoraSalida(ahora);
        }
        Propietario propietario = fachada.ObtenerPropietarioPorPatente(v.getPatente());
        if (estadia.getFechaHoraEntrada() == null) {
            System.err.println("Error: La fecha de entrada es null para la estadía en cochera " + c.getCodigo());
            return;
        }
        long segundos = ChronoUnit.SECONDS.between(estadia.getFechaHoraEntrada(), estadia.getFechaHoraSalida());
        if (!c.isOcupada()) {
            estadia.setFechaHoraEntrada(ahora);
            Anomalia anomalia = new Anomalia(ahora, "MISTERY", estadia);
            fachada.cargarListaAnomalia(anomalia);
        } else if (!v.getPatente().equals(estadia.getVehiculo().getPatente()) && c.isOcupada()) {
            Anomalia anomalia1 = new Anomalia(ahora, "TRANSPORTADOR1", estadia);
            Anomalia anomalia2 = new Anomalia(ahora, "TRANSPORTADOR2", estadia);
            fachada.cargarListaAnomalia(anomalia1);
            fachada.cargarListaAnomalia(anomalia2);
        }
        fachada.actualizarFactorDemanda();
        if(segundos <= 0){
            segundos = 1;
        }
        parking.calcularValorBaseEstadia(v.getTipo(), estadia, segundos);
        parking.setCocherasOcupadas(parking.getCocherasOcupadas() - 1);
        double total = propietario.getCuentaCorriente() - parking.getMultas();
        propietario.setCuentaCorriente(total);
        c.setOcupada(false);
        v.setEstacionado(false);
    }

}
