package Controladores;

import Observador.Observable;
import static Observador.Observable.Evento.ACTUALIZAR_DISPONIBILIDAD;
import static Observador.Observable.Evento.ACTUALIZAR_PRECIOS;
import static Observador.Observable.Evento.INGRESAR_ANOMALIA;
import Sistemas.Fachada;
import clasesDeEjemplo.Parking;
import clasesDeEjemplo.Anomalia;
import clasesDeEjemplo.Estadia;
import clasesDeEjemplo.Tarifario;
import Excepciones.ParkingException;
import Excepciones.AnomaliaException;
import Excepciones.PrecioInvalidoException;
import Excepciones.DisponibilidadException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingController extends Observable {

    private Fachada fachada = Fachada.getInstancia();
    private boolean monitoringAnomalies = false;
    private Thread anomalyThread;

    public ArrayList<Parking> obtenerParkings() {
        return fachada.ObtenerParkings();
    }

    public ArrayList<Anomalia> obtenerAnomalias() {
        return fachada.ObtenerAnomalias();
    }

    public Parking obtenerParkingPorNombre(String nombre) throws ParkingException {
        Parking parking = fachada.ObtenerParkingPorNombre(nombre);
        if (parking == null) {
            throw new ParkingException("No se encontró el parking con el nombre: " + nombre);
        }
        return parking;
    }

    public int getTotalEstadias() {
        return fachada.ObtenerEstadias().size();
    }

    public double getTotalFacturacion() {
        ArrayList<Parking> parkings = fachada.ObtenerParkings();
        double totalFacturacion = 0.0;

        for (int i = 0; i < parkings.size(); i++) {
            Parking parking = parkings.get(i);
            totalFacturacion += parking.getMultas() + parking.getSubtotal();
        }

        return totalFacturacion;
    }

    public Object[][] getParkingData() {
        ArrayList<Parking> parkings = fachada.ObtenerParkings();
        Object[][] data = new Object[parkings.size()][8];

        for (int i = 0; i < parkings.size(); i++) {
            Parking parking = parkings.get(i);
            data[i][0] = parking.getNombre();
            data[i][1] = parking.getCocherasOcupadas();
            data[i][2] = parking.getCocherasLibres();
            data[i][3] = parking.getTendencia();
            data[i][4] = parking.getFactorDemanda();
            data[i][5] = parking.getEstadias().size();
            data[i][6] = parking.getMultas();
            data[i][7] = parking.getSubtotal();
        }

        return data;
    }

    public Object[][] getAnomaliesData() {
        ArrayList<Anomalia> anomalias = fachada.ObtenerAnomalias();
        Object[][] data = new Object[anomalias.size()][4];

        for (int i = 0; i < anomalias.size(); i++) {
            Anomalia anomalia = anomalias.get(i);
            data[i][0] = anomalia.getFechaHora();
            data[i][1] = fachada.ObtenerPropietarioPorPatente(anomalia.getEstadia().getVehiculo().getPatente());
            data[i][2] = anomalia.getCodigo();
            data[i][3] = anomalia.getEstadia().getCochera();
        }

        return data;
    }

    public void startMonitoringAnomalies() throws AnomaliaException {
        if (!monitoringAnomalies) {
            monitoringAnomalies = true;

            anomalyThread = new Thread(() -> {
                while (monitoringAnomalies) {
                    detectarAnomalias();
                    try {
                        Thread.sleep(5000); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); 
                    }
                }
            });
            anomalyThread.start();
        } else {
            throw new AnomaliaException("El monitoreo de anomalías ya está en ejecución.");
        }
    }

    public void stopMonitoringAnomalies() throws AnomaliaException {
        if (monitoringAnomalies) {
            monitoringAnomalies = false;
            if (anomalyThread != null && anomalyThread.isAlive()) {
                anomalyThread.interrupt();
                anomalyThread = null;
            }
        } else {
            throw new AnomaliaException("El monitoreo de anomalías no está en ejecución.");
        }
    }

    private void detectarAnomalias() {
        ArrayList<Parking> parkings = fachada.ObtenerParkings();

        for (Parking parking : parkings) {
            int cocherasOcupadas = parking.getCocherasOcupadas();
            int estadiasActivas = parking.getEstadias().size();

            if (cocherasOcupadas > estadiasActivas) {
                LocalDateTime now = LocalDateTime.now();
                Estadia estadia = parking.getEstadias().stream().findFirst().orElse(null);
                if (estadia != null) {
                    Anomalia nuevaAnomalia = new Anomalia(now, "Discrepancia de ocupación", estadia);
                    try {
                        addAnomaly(nuevaAnomalia);
                    } catch (AnomaliaException e) {
                        e.printStackTrace(); 
                    }
                }
            }
        }
    }

    public void addAnomaly(Anomalia anomaly) throws AnomaliaException {
        ArrayList<Anomalia> anomalias = fachada.ObtenerAnomalias();
        if (monitoringAnomalies && !anomalias.contains(anomaly)) {
            anomalias.add(anomaly);
            avisar(INGRESAR_ANOMALIA);
        } else {
            throw new AnomaliaException("Anomalía ya registrada o el monitoreo no está activo.");
        }
    }

    public Object[][] getPreciosData(Parking parking) {
        Tarifario tarifario = parking.getTarifario();
        Object[][] data = {
            {"Motocicleta", tarifario.getTarifaMoto()},
            {"Standard", tarifario.getTarifaStandard()},
            {"Carga", tarifario.getTarifaCarga()},
            {"Pasajeros", tarifario.getTarifaPasajeros()}
        };
        return data;
    }

    public String actualizarPrecio(Parking parking, String tipoVehiculo, double nuevoValor) {
        if (nuevoValor < 0) {
            return "Valor inválido. El precio debe ser igual o mayor a cero.";
        }

        double promedioPrecio = calcularPromedioPrecio(parking.getTarifario());
        if (nuevoValor >= 2 * promedioPrecio) {
            return "Valor demasiado alto. El sistema no permite dispersión de precios por encima del 100%. Ingrese un valor menor a " + (2 * promedioPrecio) + ".";
        }

        parking.actualizarTarifa(tipoVehiculo, nuevoValor);
        avisar(ACTUALIZAR_PRECIOS);  
        return "";
    }

    private double calcularPromedioPrecio(Tarifario tarifario) {
        double total = tarifario.getTarifaMoto() + tarifario.getTarifaStandard() + tarifario.getTarifaCarga() + tarifario.getTarifaPasajeros();
        return total / 4;
    }

    public Object[][] getCocherasData(Parking parking) {
        Object[][] data = {
            {"Discapacitado", parking.getCocherasDiscapacitado()},
            {"Electrico", parking.getCocherasElectrico()},
            {"Empleado", parking.getCocherasEmpleado()}
        };

        return data;
    }

    public void actualizarDisponibilidad(Parking parking) throws DisponibilidadException {
        try {
            int cocherasOcupadas = fachada.obtenerCocherasOcupadas(parking);
            int cocherasLibres = fachada.obtenerCocherasLibres(parking);

            parking.setCocherasOcupadas(cocherasOcupadas);
            

            
            avisar(Observable.Evento.ACTUALIZAR_DISPONIBILIDAD); 
        } catch (Exception e) {
            throw new DisponibilidadException("Error al actualizar la disponibilidad: " + e.getMessage());
        }
    }
}
