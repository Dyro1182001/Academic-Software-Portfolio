/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesPolimorfismo;

/**
 *
 * @author Drakmalar
 */

public abstract class Tarifa {
    protected double tarifa;

    public abstract double calcularTarifa(double minutos, double factorDemanda);
}

