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
public class VehiculoElectrico implements EtiquetaVehiculo {
    @Override
    public boolean esDiscapacitado() {
        return false;
    }

    @Override
    public boolean esElectrico() {
        return true;
    }

    @Override
    public boolean esEmpleado() {
        return false;
    }
}
