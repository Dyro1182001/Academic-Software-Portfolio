//Rodrigo delgado 299328
//Bruno iglesias 305558
package dominio;

import Estructuras.ListaVuelo;

public class Conexion implements Comparable<Conexion> {
    public String codigoAeropuertoOrigen;
    public String codigoAeropuertoDestino;
    public double kilometros;
    public ListaVuelo vuelos;

    public Conexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double kilometros) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
        this.kilometros = kilometros;
        this.vuelos=new ListaVuelo();
    }

    public String getCodigoAeropuertoOrigen() {
        return codigoAeropuertoOrigen;
    }

    public void setCodigoAeropuertoOrigen(String codigoAeropuertoOrigen) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
    }

    public String getCodigoAeropuertoDestino() {
        return codigoAeropuertoDestino;
    }

    public void setCodigoAeropuertoDestino(String codigoAeropuertoDestino) {
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
    }

    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    //VERIFICARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
    public int compareTo(Conexion c) {

        int comparacionOrigen = this.codigoAeropuertoOrigen.compareTo(c.codigoAeropuertoOrigen);


        if (comparacionOrigen == 0) {
            return this.codigoAeropuertoDestino.compareTo(c.codigoAeropuertoDestino);
        }

        return comparacionOrigen;
    }


}
