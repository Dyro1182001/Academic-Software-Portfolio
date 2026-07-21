/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesPolimorfismo;

/**
 *
 * @author Drakmalar
 */
public class MultaEmpleado extends Multa {
    @Override
    public double calcularMulta(double minutos, double precioBase) {
        return (minutos / 10) * 1;
    }
}
