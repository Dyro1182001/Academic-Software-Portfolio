/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesPolimorfismo;

import Interfaces.EtiquetaVehiculo;

/**
 *
 * @author Drakmalar
 */
public class VehiculoDiscapacitado implements EtiquetaVehiculo {
    @Override
    public boolean esDiscapacitado() {
        return true;
    }

    @Override
    public boolean esElectrico() {
        return false;
    }

    @Override
    public boolean esEmpleado() {
        return false;
    }
}
