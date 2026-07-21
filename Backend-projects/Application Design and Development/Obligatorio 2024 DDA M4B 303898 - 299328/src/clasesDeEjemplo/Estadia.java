/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import Interfaces.Constante;

/**
 *
 * @author Drakmalar
 */
public class Estadia {

    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private Cochera cochera;
    private double valorFacturado;
    private Vehiculo vehiculo;

    public Estadia(LocalDateTime fechaHoraEntrada, Cochera cochera, Vehiculo vehiculo) {
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.cochera = cochera;
        this.valorFacturado = 0;
        this.vehiculo = vehiculo;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public void setCochera(Cochera cochera) {
        this.cochera = cochera;
    }

    public double getValorFacturado() {
        return valorFacturado;
    }

    public void setValorFacturado(double valorFacturado) {
        this.valorFacturado = valorFacturado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

}
