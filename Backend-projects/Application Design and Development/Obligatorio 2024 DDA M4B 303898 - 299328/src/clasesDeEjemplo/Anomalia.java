/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Drakmalar
 */
public class Anomalia {

    private Estadia estadia;
    private LocalDateTime fechaHora;
    private String codigo;

    public Anomalia(LocalDateTime fechaHora, String codigo, Estadia estadia) {
        this.fechaHora = fechaHora;
        this.codigo = codigo;
        this.estadia = estadia;
    }

    public Estadia getEstadia() {
        return estadia;
    }

    public void setEstadia(Estadia estadia) {
        this.estadia = estadia;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
