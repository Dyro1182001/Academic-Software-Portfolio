/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observador;

/**
 *
 * @author Drakmalar
 */
import java.util.ArrayList;
import java.util.Collection;

public abstract class Observable {

    private final Collection<Observador> observadores = new ArrayList<>();

    public enum Evento {
        INGRESAR_ANOMALIA,
        INGRESAR_EGRESAR_ESTADIA,
        AGREGAR_MULTA,
        NUEVA_COCHERA,
        ACTUALIZAR_DISPONIBILIDAD,
        ACTUALIZAR_PRECIOS
    }

    public void agregar(Observador observador) {
        observadores.add(observador);
    }

    public boolean quitar(Observador observador) {
        return observadores.remove(observador);
    }

    protected void avisar(Evento evento) {
        for (Observador o : observadores) {
            o.actualizar(this, evento);
        }
    }
}
