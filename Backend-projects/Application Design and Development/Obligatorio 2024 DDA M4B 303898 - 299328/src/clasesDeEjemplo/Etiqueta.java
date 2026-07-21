/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesDeEjemplo;

import java.util.Objects;

/**
 *
 * @author Drakmalar
 */
public class Etiqueta {

    private String descripcion;

    public Etiqueta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Etiqueta etiqueta = (Etiqueta) o;
        return descripcion.equals(etiqueta.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion);
    }

}
