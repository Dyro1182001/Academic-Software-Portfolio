/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import java.util.ArrayList;


public class Propietario {

    private String cedula;
    private String nombreCompleto;
    private double cuentaCorriente;
    private ArrayList<Vehiculo> vehiculos;

    public Propietario(String cedula, String nombreCompleto, double cuentaCorriente, ArrayList<Vehiculo> vehiculos) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.cuentaCorriente = cuentaCorriente;
        this.vehiculos = vehiculos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public double getCuentaCorriente() {
        return cuentaCorriente;
    }

    public void setCuentaCorriente(double cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public Vehiculo getVehiculoPorPatente(String patente) {
        for (Vehiculo v : vehiculos) {
            if (v.getPatente().equals(patente)) {
                return v;
            }
        }
        return vehiculos.getFirst();
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

}
