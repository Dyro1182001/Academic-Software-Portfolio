/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

/**
 *
 * @author Drakmalar
 */
public class Tarifario {

    private double tarifaMoto;
    private double tarifaStandard;
    private double tarifaCarga;
    private double tarifaPasajeros;

    public Tarifario(double tarifaMoto, double tarifaStandard, double tarifaCarga, double tarifaPasajeros) {
        this.tarifaMoto = tarifaMoto;
        this.tarifaStandard = tarifaStandard;
        this.tarifaCarga = tarifaCarga;
        this.tarifaPasajeros = tarifaPasajeros;
    }

    public double getTarifaMoto() {
        return tarifaMoto;
    }

    public void setTarifaMoto(double tarifaMoto) {
        this.tarifaMoto = tarifaMoto;
    }

    public double getTarifaStandard() {
        return tarifaStandard;
    }

    public void setTarifaStandard(double tarifaStandard) {
        this.tarifaStandard = tarifaStandard;
    }

    public double getTarifaCarga() {
        return tarifaCarga;
    }

    public void setTarifaCarga(double tarifaCarga) {
        this.tarifaCarga = tarifaCarga;
    }

    public double getTarifaPasajeros() {
        return tarifaPasajeros;
    }

    public void setTarifaPasajeros(double tarifaPasajeros) {
        this.tarifaPasajeros = tarifaPasajeros;
    }

}
