/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import ClasesPolimorfismo.MultaEmpleado;
import ClasesPolimorfismo.Tarifa;
import ClasesPolimorfismo.TarifaStandard;
import ClasesPolimorfismo.TarifaPasajero;
import ClasesPolimorfismo.TarifaCarga;
import ClasesPolimorfismo.MultaElectrico;
import ClasesPolimorfismo.MultaDiscapacitado;
import ClasesPolimorfismo.TarifaMoto;
import ClasesPolimorfismo.Multa;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import Interfaces.Constante;
import Observador.Observable;
import java.time.Duration;

/**
 *
 * @author Drakmalar
 */
public class Parking extends Observable {

    private String nombre;
    private String direccion;
    private String tendencia;
    private ArrayList<Cochera> cocheras;
    private int cocherasOcupadas;
    private Map<String, Tarifa> tarifas;
    private Tarifario tarifario;
    private double factorDemanda;
    private ArrayList<Estadia> estadias;
    private double multas;
    private double subtotal;

    public Parking(String nombre, String direccion, ArrayList<Cochera> cocheras, Tarifario tarifario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tendencia = "ESTABLE";
        this.cocheras = cocheras;
        this.cocherasOcupadas = 0;
        this.tarifas = new HashMap<>();
        this.tarifas.put("MOTOCICLETA", new TarifaMoto(tarifario.getTarifaMoto()));
        this.tarifas.put("CARGA", new TarifaCarga(tarifario.getTarifaCarga()));
        this.tarifas.put("PASAJEROS", new TarifaPasajero(tarifario.getTarifaPasajeros()));
        this.tarifas.put("STANDARD", new TarifaStandard(tarifario.getTarifaStandard()));
        this.tarifario = tarifario;
        this.estadias = new ArrayList<>();
        this.factorDemanda = 1.0;
        this.multas = 0.0;
        this.subtotal = 0.0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTendencia() {
        return tendencia;
    }

    public double getMultas() {
        return multas;
    }

    public void setMultas(double multas) {
        this.multas += multas;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Cochera> getCocheras() {
        return cocheras;
    }

    public Cochera DevolverCochera(String codigoCochera) {
        for (Cochera c : cocheras) {
            if (c.getCodigo() == null ? codigoCochera == null : c.getCodigo().equals(codigoCochera)) {
                return c;
            }
        }
        return cocheras.getFirst();
    }

    public void setCocheras(ArrayList<Cochera> cocheras) {
        this.cocheras = cocheras;
    }

    public Tarifario getTarifario() {
        return tarifario;
    }

    public double getFactorDemanda() {
        return factorDemanda;
    }

    public int getCocherasOcupadas() {
        return cocherasOcupadas;
    }

    public void setCocherasOcupadas(int cocherasOcupadas) {
        this.cocherasOcupadas = cocherasOcupadas;
    }

    public int getCocherasLibres() {
        return this.getCocheras().size() - cocherasOcupadas;
    }

    public int getTotalCocheras() {
        return this.getCocheras().size();
    }

    public void setFactorDemanda(double factorDemanda) {
        this.factorDemanda = factorDemanda;
    }

    public ArrayList<Estadia> getEstadias() {
        return estadias;
    }

    public void agregarCochera(Cochera cochera) {
        cocheras.add(cochera);
//        avisar("Nueva cochera agregada");
    }

    public void agregarEstadia(Estadia estadia) {
        estadias.add(estadia);
        cocherasOcupadas++;
//        avisar();
    }

    public void calcularValorBaseEstadia(String tipoVehiculo, Estadia estadia, double minutos) {
        Tarifa tarifa = tarifas.get(tipoVehiculo.toUpperCase());
        if (tarifa == null) {
            tarifa = tarifas.get("STANDARD");
        }

        double total = tarifa.calcularTarifa(minutos, factorDemanda);
        this.setSubtotal(total);
        estadia.setValorFacturado(total);
        this.agregarMulta(estadia, minutos, total);
    }

    private void agregarMulta(Estadia estadia, double minutos, double precioBase) {
        ArrayList<Etiqueta> etiquetasCochera = estadia.getCochera().getEtiquetas();
        ArrayList<Etiqueta> etiquetasVehiculo = estadia.getVehiculo().getEtiquetas();

        for (Etiqueta etiquetaCochera : etiquetasCochera) {
            String descripcion = etiquetaCochera.getDescripcion();
            boolean etiquetaPresente = false;

            for (Etiqueta etiquetaVehiculo : etiquetasVehiculo) {
                if (etiquetaVehiculo.getDescripcion().equals(descripcion)) {
                    etiquetaPresente = true;
                    break;
                }
            }

            if (!etiquetaPresente) {
                Multa multa = crearMulta(descripcion);
                if (multa != null) {
                    this.setMultas(multa.calcularMulta(minutos, precioBase));
                }
            }
        }
    }

    public void actualizarTarifa(String tipoVehiculo, double nuevoPrecio) {
        switch (tipoVehiculo.toUpperCase()) {
            case "MOTOCICLETA":
                tarifario.setTarifaMoto(nuevoPrecio);
                break;
            case "STANDARD":
                tarifario.setTarifaStandard(nuevoPrecio);
                break;
            case "CARGA":
                tarifario.setTarifaCarga(nuevoPrecio);
                break;
            case "PASAJEROS":
                tarifario.setTarifaPasajeros(nuevoPrecio);
                break;
        }
    }

    private Multa crearMulta(String descripcion) {
        switch (descripcion) {
            case "DISCAPACITADO":
                return new MultaDiscapacitado();
            case "ELECTRICO":
                return new MultaElectrico();
            case "EMPLEADO":
                return new MultaEmpleado();
            default:
                return null;
        }
    }

    public void actualizarFactorDemanda(int ingresos, int egresos) {
        int capacidad = cocheras.size();
        int diferencia = ingresos - egresos;
        int constante = Constante.UnidadTiempo;
        double ocupacion = (double) ingresos / capacidad;

        if (diferencia <= 0.1 * capacidad && diferencia >= -0.1 * capacidad) {
            factorDemanda -= 0.01;
            this.tendencia = "ESTABLE";

            if (factorDemanda < 0.25) {
                factorDemanda = 0.25;
            }
        } else if (diferencia > 0.1 * capacidad) {
            this.tendencia = "POSITIVA";
            if (ocupacion < 0.33) {
                factorDemanda += 0.05;
            } else if (ocupacion >= 0.33 && ocupacion <= 0.66) {
                factorDemanda += 0.1;
            } else {
                factorDemanda += 0.15;
            }
            if (factorDemanda > 10) {
                factorDemanda = 10;
            }
        } else {
            this.tendencia = "NEGATIVA";
            if (factorDemanda > 1) {
                factorDemanda = 1;
            } else {
                factorDemanda -= 0.05;
                if (factorDemanda < 0.25) {
                    factorDemanda = 0.25;
                }
            }
        }
    }

    public int getCocherasDiscapacitado() {
        int cantidad = 0;
        for (Cochera c : cocheras) {
            if (c.getEtiquetas().contains(new Etiqueta("DISCAPACITADO")) && !c.isOcupada()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int getCocherasElectrico() {
        int cantidad = 0;
        for (Cochera c : cocheras) {
            if (c.getEtiquetas().contains(new Etiqueta("ELECTRICO")) && !c.isOcupada()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int getCocherasEmpleado() {
        int cantidad = 0;
        for (Cochera c : cocheras) {
            if (c.getEtiquetas().contains(new Etiqueta("EMPLEADO")) && !c.isOcupada()) {
                cantidad++;
            }
        }
        return cantidad;
    }

}
