/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import Interfaces.EtiquetaVehiculo;
import java.util.ArrayList;
import simuladortransito.Estacionable;

/**
 *
 * @author PC
 */
public class Cochera implements Estacionable, EtiquetaVehiculo {

    private String codigo;
    private boolean ocupada;
    private ArrayList<Etiqueta> etiquetas;

    public Cochera(String codigo, ArrayList<Etiqueta> etiquetas) {
        this.codigo = codigo;
        this.ocupada = false;
        this.etiquetas = etiquetas;
    }

    public void setOcupada(boolean ocupado) {
        if (ocupado == true) {
            this.ocupada = true;
        } else {
            this.ocupada = false;
        }
    }

    public boolean isOcupada() {
        return this.ocupada;
    }

    public ArrayList<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean esDiscapacitado() {
        return equals("DISCAPACITADO");
    }

    @Override
    public boolean esElectrico() {
        return equals("ELECTRICO");
    }

    @Override
    public boolean esEmpleado() {
        return equals("EMPLEADO");
    }


    public void agregarEstadia(Estadia estadia) {

    }

    @Override
    public String toString() {
        return codigo + " Ocupada: " + (ocupada ? "SI" : "NO");
    }

}
