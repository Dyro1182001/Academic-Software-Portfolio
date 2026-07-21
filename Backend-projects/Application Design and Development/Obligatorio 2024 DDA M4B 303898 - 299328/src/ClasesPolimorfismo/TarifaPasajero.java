/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesPolimorfismo;

import Interfaces.Constante;

/**
 *
 * @author Drakmalar
 */
public class TarifaPasajero extends Tarifa {
    public TarifaPasajero(double tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public double calcularTarifa(double minutos, double factorDemanda) {
        return tarifa * (minutos / Constante.UnidadTiempo) * factorDemanda;
    }
}
