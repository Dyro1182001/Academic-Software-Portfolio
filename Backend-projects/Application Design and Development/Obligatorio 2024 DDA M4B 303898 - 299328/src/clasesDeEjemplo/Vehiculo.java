/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import java.util.ArrayList;
import simuladortransito.Transitable;
import Interfaces.EtiquetaVehiculo;


public class Vehiculo implements Transitable, EtiquetaVehiculo {

    private String patente;
    private String tipo;
    private boolean estacionado;
    private ArrayList<Etiqueta> etiquetas;

    public Vehiculo(String patente, String tipo, ArrayList<Etiqueta> etiquetas) {
        this.patente = patente;
        this.tipo = tipo;
        this.etiquetas = etiquetas;
    }

    public void setEstacionado(boolean estacionado) {
        this.estacionado = estacionado;
    }

    public boolean isEstacionado() {
        return estacionado;
    }

    @Override
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Etiqueta etiqueta) {
        this.etiquetas.add(etiqueta);
    }

    @Override
    public boolean esDiscapacitado() {
        return tipo.equals("DISCAPACITADO");
    }

    @Override
    public boolean esElectrico() {
        return tipo.equals("ELECTRICO");
    }

    @Override
    public boolean esEmpleado() {
        return tipo.equals("EMPLEADO");
    }

    @Override
    public String toString() {
        return patente + " Tipo: " + tipo + " Estacionado: " + (estacionado ? "SI" : "NO");
    }

}
