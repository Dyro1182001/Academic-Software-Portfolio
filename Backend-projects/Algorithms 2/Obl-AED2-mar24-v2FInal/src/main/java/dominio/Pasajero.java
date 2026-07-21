//Rodrigo delgado 299328
//Bruno iglesias 305558
package dominio;

import interfaz.Categoria;

import java.util.Objects;

public class Pasajero implements Comparable<Pasajero> {
    public String cedula;
    public String nombre;
    public String telefono;
    public Categoria categoria;

    public Pasajero(String cedula, String nombre, String telefono, Categoria categoria) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.categoria = categoria;
    }

    public Pasajero() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public boolean ValidarCedula(String cedula){

        String Formatovalidador = "^([1-9]\\.\\d{3}\\.\\d{3}-\\d{1}|[1-9]{3}\\.\\d{3}-\\d{1})$";
        if (cedula.matches(Formatovalidador)){

            return true;
        }
        return false;

    }

    public int compareTo(Pasajero p) {
        return this.cedula.compareTo(p.cedula);
    }

    @Override
    public String toString() {
            return getCedula()+";"+getNombre()+";"+getTelefono()+";"+getCategoria().getTexto()+"|" ;
    }
}
